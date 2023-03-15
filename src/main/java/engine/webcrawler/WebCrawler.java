/** abstract class specifying webcrawler typical implementation classes and url
 * implement: *Crawler classes */

package engine.webcrawler;

import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
public abstract class WebCrawler
{
	protected final String url; //necessary at all? maybe later

	protected WebCrawler(String url)
	{
		this.url = url;
	}

	abstract ChromeDriver driverInit();
	abstract void downloadSelenium()  throws InterruptedException, IOException;

}