//** sometimes works sometimes not */

package engine.webcrawler;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TwitterCrawler extends WebCrawler
{
	private ArrayList<String[]> tweets;
	private final String url;
	private String accountname;

//constructor
	public TwitterCrawler(String accountname, String url) throws InterruptedException, IOException
	{
		super(url);

		this.url=url;
		this.accountname=accountname;
		downloadSelenium();
		var x=new Output();
		x.fileWriteTweets("twitteroutput.txt", tweets, accountname,3,-4);
	}

	@Override
	ChromeDriver driverInit()
	{
		final ChromeOptions options = new ChromeOptions();
		options.addArguments(
				"--headless",
				"--remote-allow-origins=*"
		);
		return 	new ChromeDriver(options);
	}

	@Override
	void downloadSelenium() throws InterruptedException
	{
//######VARIABLES
		//[data-testid='tweet']

		var driver = driverInit();
		this.tweets = new ArrayList<>();

		List<WebElement> tweetElements;
		int sorszam = 0;
		int scrolldowns = 5;

		driver.get(url);

		WebElement twitterPage =
				new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", twitterPage);

		for (int i = 0; i < scrolldowns; i++)
		{
			tweetElements = twitterPage.findElements(By.cssSelector("[data-testid='tweet']"));

			sorszam++;

			for (WebElement x : tweetElements)
			{
//*************** numbering tweet blocks

				List<String> lst = Arrays.asList(sorszam + "  ", x.getText());
				String r = lst.stream().collect(Collectors.joining("  "));

				tweets.add(r.split("\n"));
//***************
			}
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,800);", "");
			TimeUnit.MILLISECONDS.sleep(new Random().nextInt(514, 778));
		}
		//String comparator = String.valueOf(sorszam);

		driver.quit();
	}
}