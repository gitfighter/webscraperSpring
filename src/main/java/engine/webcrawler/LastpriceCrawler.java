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

public class LastpriceCrawler extends WebCrawler
{
	private String elementValue;
	protected final String element;
	private final String name;
	private int timeout=0; //wait for element visibility

//constuctor starts processing input sent by Router, calls functions
	public LastpriceCrawler(String name, String url, String element) throws InterruptedException
	{
		super(url);
		this.name=name;

		if(url.contains("frankfurt"))
		{
			this.element="/html/body/app-root/app-wrapper/div/div[2]/app-bond/div[2]/div[2]/div[2]/div/div[1]/app-widget-price-box/div/div/table/tbody/tr[1]/td[2]";
			this.timeout=3;
		}
		else if(url.contains("investing.com"))
		{
			this.element="span.text-2xl";
			this.timeout=1;
		}
		else if(url.contains("bloomberg.com"))
		{
			this.element="span.priceText__0550103750";
			this.timeout=1;
		}
		else if(url.contains("akk.hu"))
		{
			timeout=3;
			this.element=element;
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
			//TimeUnit.MILLISECONDS.sleep(new Random().nextInt(514,778));
			downloadSeleniumNullCheck();

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

				WebElement lastPrice =
						new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", lastPrice);
				this.elementValue =lastPrice.getText();

			driver.quit();

		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private void downloadSeleniumNullCheck()
	{
		try
		{
			final ChromeOptions options=new ChromeOptions();
			options.addArguments("--headless");
			var driver= new ChromeDriver(options);

			driver.get(url);
			while(this.elementValue==null)
			{
				WebElement lastPrice =
						new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", lastPrice);
				this.elementValue = lastPrice.getText();
			}

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
