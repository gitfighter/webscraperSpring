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

public class LastpriceCrawler extends WebCrawler
{
	private String elementValue;
	protected final String element;
	private final String name;

//constuctor starts processing input sent by Router, calls functions
	public LastpriceCrawler(String name, String url, String element) throws InterruptedException
	{
		super(url);
		this.name=name;

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

//sends elementvalue 1 by 1
	@Override
	void downloadJsoup()
	{
			try
			{
				this.elementValue = Jsoup.connect(super.url).get().select(element).text();
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}

	}

//sends elementvalue 1 by 1
	@Override
	void downloadSelenium()
	{
		try
		{
			final ChromeOptions options=new ChromeOptions();
			options.addArguments("--headless");
			var driver= new ChromeDriver(options);

			driver.get(url);

			WebElement lastPrice=
					new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", lastPrice);

			this.elementValue =lastPrice.getText();

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

	public String getName()
	{
		return name;
	}
}
