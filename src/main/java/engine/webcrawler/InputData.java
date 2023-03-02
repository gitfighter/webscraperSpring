/** reads data into a 2d string array */

package engine.webcrawler;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;

public class InputData
{
	private String filename;
	private int csvsize;
	private String[][] inputElements;

	protected InputData(String filename, int csvsize) throws IOException
	{
		this.filename = filename;
		this.csvsize=csvsize;
		this.inputElements=csvTo2dArray(filename, 3);
	}

	private static String[][] csvTo2dArray (String filename, int csvsize) throws IOException
	{
		File openedFile = new File(filename);
		String openedFileParse = Jsoup.parse(openedFile, null).text();


		String[] inputData = openedFileParse.split(";");

		String[][] inputElements = new String[inputData.length][csvsize];

		for (int i = 0; i < inputData.length; i++)
		{
			inputElements[i] = inputData[i].split(",");
		}

		return inputElements;
	}

	protected String[] urlArray () //gives url list from csv
	{
		String [] urlArray=new String[inputElements.length];

		for (int i=0;i< urlArray.length;i++)
		{
			urlArray[i]=inputElements[i][csvsize-1]; //always last element
		}

		return urlArray;
	}

	protected String[] elementArray() //gives element list from csv
	{
		String [] elementArray=new String[inputElements.length];

		for (int i=0;i< elementArray.length;i++)
		{
			elementArray[i]=inputElements[i][csvsize-2]; //generalize csvsize-2 later
		}

		return elementArray;
	}

	protected String[] tickerArray() //gives ticker list from csv
	{
		String [] tickerArray=new String[inputElements.length];

		for (int i=0;i< tickerArray.length;i++)
		{
			tickerArray[i]=inputElements[i][0]; //always 1st element
		}

		return tickerArray;
	}

	protected String[][] getInputElements()
	{
		return inputElements;
	}

	protected String[] getURLArray ()
	{
		return urlArray ();
	}

	protected String[] getElementArray()
	{
		return elementArray();
	}

	protected String[] getTickerArray()
	{
		return tickerArray();
	}

}
