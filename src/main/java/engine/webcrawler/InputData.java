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
	private String[] inputArray;

	public InputData(String filename, int csvsize) throws IOException
	{
		this.filename = filename;
		this.csvsize=csvsize;
		this.inputElements=csvTo2dArray(filename, 3);
	}

	public InputData(String filename) throws IOException
	{
		this.filename = filename;
		this.inputArray=txtToArray(filename);
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

	private static String[] txtToArray(String filename) throws IOException
	{
		File openedFile = new File(filename);
		String openedFileParse = Jsoup.parse(openedFile, null).text();

		String[] inputData = openedFileParse.split(";");

		return inputData;
	}

	private String[] urlArray () //gives url list from csv
	{
		String [] urlArray=new String[inputElements.length];

		for (int i=0;i< urlArray.length;i++)
		{
			urlArray[i]=inputElements[i][csvsize-1]; //always last element
		}

		return urlArray;
	}

	private String[] elementArray() //gives element list from csv
	{
		String [] elementArray=new String[inputElements.length];

		for (int i=0;i< elementArray.length;i++)
		{
			elementArray[i]=inputElements[i][csvsize-2]; //generalize csvsize-2 later
		}

		return elementArray;
	}

	private String[] tickerArray() //gives ticker list from csv
	{
		String [] tickerArray=new String[inputElements.length];

		for (int i=0;i< tickerArray.length;i++)
		{
			tickerArray[i]=inputElements[i][0]; //always 1st element
		}

		return tickerArray;
	}

	public String[][] getInputElements()
	{
		return inputElements;
	}

	public String[] getInputArray()
	{
		return inputArray;
	}

	public String[] getURLArray ()
	{
		return urlArray ();
	}

	public String[] getElementArray()
	{
		return elementArray();
	}

	public String[] getTickerArray()
	{
		return tickerArray();
	}


}
