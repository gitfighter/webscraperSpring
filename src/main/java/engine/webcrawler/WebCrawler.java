/** downloads pages and passes them for further processing */

package engine.webcrawler;

import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WebCrawler
{
	private String elementValue;
	private String url;
	private String element;

	protected WebCrawler(String url, String element) throws InterruptedException
	{
		this.url = url;

		if(url.contains("frankfurt"))
		{
			this.element="/html/body/app-root/app-wrapper/div/div[2]/app-bond/div[2]/div[2]/div[2]/div/div[1]/app-widget-price-box/div/div/table/tbody/tr[1]/td[2]";
		}
		else if(url.contains("investing.com"))
		{
			this.element="span.text-2xl";
		}
		else if(url.contains("bloomberg.com"))
		{
			this.element="span.priceText__0550103750";
		}
		else
		{
			this.element=element;
		}


		if (
				url.contains("frankfurt") ||
				url.contains(("akk.hu"))
			)
		{
			TimeUnit.MILLISECONDS.sleep(new Random().nextInt(814,1178)); //basic antibot protection
			downloadSelenium();
		}
		else
			downloadJsoup();
	}

	private void downloadJsoup()
	{
		try
		{
			this.elementValue = Jsoup.connect(this.url).get().select(element).text();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void downloadSelenium()
	{
		try
		{
			final ChromeOptions options=new ChromeOptions();
			options.addArguments("--headless");
			ChromeDriver driver=new ChromeDriver(options);

			driver.get(url);

			WebElement lastPrice=
					new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", lastPrice);
			//final WebElement lastPrice= driver.findElement(By.xpath(element));

			this.elementValue=lastPrice.getText();

			driver.quit();

		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	protected String getElementValue()
	{

		return this.elementValue;
	}
}