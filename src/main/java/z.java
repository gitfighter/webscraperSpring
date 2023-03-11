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
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class z
{
	public static void main(String[] args)
	{

		//*[@id='react-root']/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/section/div/div/div[4]/div/div/article/div/div/div[2]/div[2]/div[2]

		try
		{
			final ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			var driver = new ChromeDriver(options);

			driver.get("https://twitter.com/zerohedge");

			WebElement twitterPage =
					new WebDriverWait(driver, Duration.ofSeconds(5)).
							until(ExpectedConditions.
									visibilityOfElementLocated(
											By.xpath("//*[@id='react-root']/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/section/div")));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", twitterPage);

			ArrayList<String[]> tweets = new ArrayList<>();

			for (int i = 0; i < 2; i++)
			{
				((JavascriptExecutor) driver).executeScript("window.scrollBy(0,800)", "");
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(211, 378));
				List<WebElement> temp = twitterPage.findElements(By.xpath("//*[@id='react-root']/div/div/div[2" +
						"]/main/div/div/div" +
						"/div" +
						"[1]/div/div[3]/div/div/section/div/div/div[4]/div/div/article/div/div/div[2]/div[2]/div[2]"));

			}

			//		var output=new Output();
			//		output.arraylistContainingStringArrayToFile();

			driver.quit();

		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}



	public String[] seleniumListToStringArray(List<WebElement> webelement)
	{
//split into String[] put in arraylist creating a 2d arraylist/array
		String[] stringarray = new String[webelement.size()];

		for (int i = 1; i < webelement.size(); i++)
		{
//convert from WebElement to String[]
			stringarray[i] = webelement.get(i).getText();
//split to String[] put in table
		}
		return stringarray;
	}
}