/** handles output to different subsystems/files */

package engine.webcrawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


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

	protected void arraylistToFile(String filename, ArrayList<String> what) throws IOException
	{
		var fileWrite = new BufferedWriter(new FileWriter(filename));
		for (String x : what)
		{
			fileWrite.write(x);
		}
		fileWrite.close();
	}

	protected void arraylistHybridToFile(String filename, ArrayList<String[]> what) throws IOException
	{
		var fileWrite = new BufferedWriter(new FileWriter(filename));
		for (String[] x : what)
		{
			for(int i=0;i<x.length;i++)
			{
				fileWrite.write(x[i]);
			}
			fileWrite.write("\n");
		}
		fileWrite.close();
	}

	protected void fwdtableToFile(String filename, ArrayList<String[]> what) throws IOException
	{
		var fileWrite = new BufferedWriter(new FileWriter(filename));
		for (String[] x : what)
		{
			for(int i=0;i<x.length;i++)
			{
				if(
						x[i].contains("1M") ||
						x[i].contains("2M") ||
						x[i].contains("3M") ||
						x[i].contains("4M") ||
						x[i].contains("5M") ||
						x[i].contains("6M") ||
						x[i].contains("9M") ||
						x[i].contains("1Y")
				)
				fileWrite.write(x[i]);

			}
			fileWrite.write("\n");
		}
		fileWrite.close();
	}
	protected void everythingToFile(String filename, String name) throws IOException
	{
		var fwdData=new ForwardCalculations();
		var tenor=fwdData.getTenor();
		var bids=fwdData.getBidfwdPercent();
		var offers=fwdData.getOfferfwdPercent();
		ArrayList<String> fwdOutput=null;

		for (int i=0;i<tenor.size();i++ )
		{
			fwdOutput.add(tenor.get(i)+" "+bids.get(i)+" "+offers.get(i) +" "+ "::Last Update: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n");
		}

		ArrayList<String> lastPrices=null;


		var fileWrite = new BufferedWriter(new FileWriter(filename));
		for (String x : lastPrices)
		{
			fileWrite.write(x);
		}
		fileWrite.write("Rough Forward Yields:+\n");
		for(String x:fwdOutput)
		{
			fileWrite.write(x);
		}
		fileWrite.close();
	}
}