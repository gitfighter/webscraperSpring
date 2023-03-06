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

public class WebCrawler
{
	private String elementValue;
	private String url;
	private String element;

	protected WebCrawler(String url, String element) throws InterruptedException
	{
		this.url = url;

//element xpaths same in the below cases
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

//using selenium or jsoup
		if (
				url.contains("frankfurt")
			)
		{
			downloadSelenium();
			//TimeUnit.MILLISECONDS.sleep(new Random().nextInt(514,778)); //basic antibot protection
		}
		else if (url.contains("akk.hu"))
		{
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
			var driver= new ChromeDriver(options);

			driver.get(url);

			WebElement lastPrice=
					new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", lastPrice);

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