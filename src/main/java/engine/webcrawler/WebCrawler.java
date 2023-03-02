/** downloads pages and passes them for further processing */

package engine.webcrawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebCrawler
{
	private Document document;
	private HtmlPage htmlPage;
	private String url;

	protected WebCrawler(String url)
	{
		this.url = url;

		if(url.contains("frankfurt"))
		{
			downloadHTMLUnit();
		}
		else
			downloadJsoup();
	}

	private void downloadJsoup()
	{
		try
		{
//##################
			this.document = Jsoup.connect(this.url).get();
//##################
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void downloadHTMLUnit()
	{
		try
		{
			final WebClient client = new WebClient(BrowserVersion.CHROME);

			client.getOptions().setThrowExceptionOnScriptError(false);
			client.getOptions().setUseInsecureSSL(true);
			client.getOptions().setCssEnabled(false);
			client.getOptions().setJavaScriptEnabled(true);
			client.waitForBackgroundJavaScript(3000);
			client.setAjaxController(new NicelyResynchronizingAjaxController());
//##################
			htmlPage = client.getPage(url);
//##################
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected Document getDocument()
	{
		return document;
	}

	protected HtmlPage getHtmlPage()
	{
		return htmlPage;
	}
}