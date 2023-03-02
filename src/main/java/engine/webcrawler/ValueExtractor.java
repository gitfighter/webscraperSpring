/** extracts data from pages */

package engine.webcrawler;

public class ValueExtractor

{
	private WebCrawler document;
	private String element;
	private String elementValue;
	private String url;


	protected ValueExtractor(WebCrawler document, String element, String url)
	{
		this.document=document;
		this.element=element;
		this.url=url;
		this.elementValue=extractElement();
	}

	private String extractElement()
	{

		if (this.url.contains("frankfurt"))
		{
			return document.getHtmlPage().asNormalizedText().substring(571, 589);
		}
		else if (this.url.contains("borsaitaliana"))
		{
			return document.getDocument().text().substring(465, 610);
		}
		else
			return document.getDocument().select(element).text();
	}

	protected String getElementValue() // from pages downloaded w/ WebCrawler class
	{
		return elementValue;
	}

}