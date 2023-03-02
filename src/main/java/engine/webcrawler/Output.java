/** handles output to different subsystems/file */

package engine.webcrawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Output
{
	protected Output()
	{
	}

	protected String getOutput(String name, String element)
	{
		System.out.println(name + " Done");
		return "\n" + name + ": " + element + " " +
				"::Last Update: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
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

}

