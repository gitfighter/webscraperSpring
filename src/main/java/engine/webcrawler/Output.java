/** handles output to different subsystems/files */

package engine.webcrawler;

import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Output
{
	public Output()
	{
	}

//########formatFwdPrice is in ForwardCalculation

	protected String formatLastPrice(String name, String element)
	{

			System.out.println(name + " Done");
			return name + ": " + element + " " +
					"::Last Update: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n";
	}

	public void arraylistStringToFile(String filename, ArrayList<String> what) throws IOException
	{
		var fileWrite = new BufferedWriter(new FileWriter(filename));
		for (String x : what)
		{
			fileWrite.write(x);
		}
		fileWrite.close();
	}

	public void arraylistStringArrayToFile(String filename, ArrayList<String[]> what)
	{
		try (FileWriter tmp=new FileWriter(filename))
		{
			var fileWrite = new BufferedWriter(tmp);
			for (String[] x : what)
			{
				for (int i=0;i<x.length;i++)
					fileWrite.write(x[i]);
			}
			fileWrite.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

//similar to arraylistStringArrayToFile(), can specify the index range of string arrays to be printed
	void fileWriteTweets(String filename, ArrayList<String[]> what, String accountname, int begin, int relativeend) throws IOException //should go to OutPut
	{
		try(BufferedWriter fileWrite = new BufferedWriter(new FileWriter(filename)))
		{
			fileWrite.write("Time of scraping: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n\n");

			for (String[] x : what)
			{
//################### >>>> megmondani neki h a kov. ojjektet writeolja fileba
				//	if(x[0].contains(comparator+"  "+accountname))
				//	{
				fileWrite.write(accountname + "\n");
				for (int i = begin; i < x.length - relativeend; i++)
				{
					fileWrite.write(x[i] + '\n');
				}
				fileWrite.write("\n");
				//	}
			}
			fileWrite.write("\n");
		}
	}
	public void fwdtableToFile(String filename, ArrayList<String[]> what)
	{
		try (FileWriter tmp=new FileWriter(filename))
		{
			var fileWrite = new BufferedWriter(tmp);
			for (String[] x : what)
			{
				for (int i = 0; i < x.length; i++)
				{
					if (

						//x[i].contains("ON") || //for test only

							x[i].contains(" 1M") ||
									x[i].contains(" 2M") ||
									x[i].contains(" 3M") ||
									x[i].contains(" 4M") ||
									x[i].contains(" 5M") ||
									x[i].contains(" 6M") ||
									x[i].contains(" 9M") ||
									x[i].contains(" 1Y")
					)
						fileWrite.write(x[i]);

				}
				fileWrite.write("\n");
			}
			fileWrite.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}