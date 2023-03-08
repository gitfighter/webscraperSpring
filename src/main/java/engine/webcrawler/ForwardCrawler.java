package engine.webcrawler;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ForwardCrawler extends WebCrawler
{
	private ArrayList<String[]> forwardTable;
	private double spot;
	private final String element;

	public ForwardCrawler(String url, String element)
	{
		super(url);
		if (url.contains("investing.com") && url.contains("forward-rates"))
		{
			this.element = "//*[@id=\"curr_table\"]";
		}
		else
			this.element=element;

		downloadSelenium();
	}
	@Override
	void downloadSelenium()
	{
		try
		{
			final ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			var driver = new ChromeDriver(options);

			driver.get(url);

//get spot rate
			WebElement lastPriceElement =
					new WebDriverWait(driver, Duration.ofSeconds(2)).
//---investing fixed spot element name
							until(ExpectedConditions.visibilityOfElementLocated(By.ByXPath.xpath("//" + "*[@id=\"last_last\"]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", lastPriceElement);

			this.spot = Double.parseDouble(lastPriceElement.getText());
//get forward table
			WebElement fwdPriceElement =
					new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(By.ByXPath.xpath(element)));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", fwdPriceElement);

//got the rows
			List<WebElement> rows = fwdPriceElement.findElements(By.tagName("tr"));

//split rows into String[] put in arraylist creating a 2d arraylist/array
			String[] rowsFinal = new String[rows.size()];
			ArrayList<String[]> fwdTable = new ArrayList<>();

			for (int i = 1; i < rows.size(); i++)
			{
//convert from WebElement to String[]
				rowsFinal[i] = rows.get(i).getText();
//split to String[] put in table
				fwdTable.add(rowsFinal[i].split(" "));
			}
			driver.quit();

			this.forwardTable=fwdTable;
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	@Override
	void downloadJsoup()
	{
	}

	public ArrayList<String[]> getForwardTable()
	{
		return forwardTable;
	}

	public double getSpot()
	{
		return spot;
	}
}
