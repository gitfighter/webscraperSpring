package engine.webcrawler;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TwitterCrawler
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
//*[@id='react-root']/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/section/div/div/div[3]/div/div/article/div/div/div[2]/div[2]/div[2]
//*[@id='react-root']/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/section/div/div/div[1]/div/div/article/div/div/div[2]
//*[@id='react-root']/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/section
//[data-testid='tweet']

			final ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			var driver = new ChromeDriver(options);

			driver.get("https://twitter.com/zerohedge");

		WebElement lastPrice=
				new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='react-root']/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/section/div")));

		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", lastPrice);

		ArrayList<String[]> tweets=new ArrayList<>();
		List<WebElement> rows;

		for (int i=0;i<3;i++)
		{
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,800);", "");
			TimeUnit.MILLISECONDS.sleep(new Random().nextInt(514,778));
			rows = lastPrice.findElements(By.cssSelector("[data-testid='tweet']"));

			for(WebElement x:rows)
			{
				var temp=x.getText();
				tweets.add(x.getText().split("\n"));
			}
		}


		driver.quit();

		try(BufferedWriter fileWrite = new BufferedWriter(new FileWriter("twitteroutput.txt")))
		{
			fileWrite.write("Time of scraping: "+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

			for (String[] x:tweets)
			{
				fileWrite.write(x[0]+"\n");

				for (int i=3;i<x.length-4;i++)
				{
					fileWrite.write(x[i]+'\n');
				}

				fileWrite.write("\n");
			}
			fileWrite.write("\n");
		}

		System.out.println("Processing Done, have a nice day!");
	}

}