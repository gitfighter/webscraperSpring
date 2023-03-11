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

	public void arraylistToFile(String filename, ArrayList<String> what) throws IOException
	{
		var fileWrite = new BufferedWriter(new FileWriter(filename));
		for (String x : what)
		{
			fileWrite.write(x);
		}
		fileWrite.close();
	}

	public void arraylistContainingStringArrayToFile(String filename, ArrayList<String[]> what)
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