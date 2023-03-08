/** abstract class specifying webcrawler typical implementation classes and url */

package engine.webcrawler;


public abstract class WebCrawler
{
	protected final String url; //necessary at all? maybe later

	protected WebCrawler(String url)
	{
		this.url = url;
	}

	abstract void downloadJsoup();
	abstract void downloadSelenium();

}