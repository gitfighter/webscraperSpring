/** main class for instructions */

package engine.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;



@SpringBootApplication
public class WebscraperSpring
{

	public static void main(String[] args)
	{
				SpringApplication.run(WebscraperSpring.class, args);

		try
		{
			var inputDummy=new InputData("tickerlist.csv", 3);
			final String[][] inputData=inputDummy.getInputElements();


			ArrayList<String> outputValues=new ArrayList<>();
			var output=new Output();

//puts array elements from csv into pipline, outputs formatted values to outputValues arraylist
			for(int j=0;j< inputData.length;j++)
			{
				outputValues.add(
						elementPipeline(
								inputData[j][0],
								inputData[j][1],
								inputData[j][2]
						)
				);
			}

//prints outputValues to file
			output.arraylistToFile("output.txt", outputValues);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected static String elementPipeline(String name, String element, String url) throws InterruptedException
	{
//#1 DOWNLOADS, EXTRACTS ELEMENTS
		var elementValue = new WebCrawler(url, element);
//#2 RETURNS FORMATTED VALUES (that main puts into arraylist outputValues)
		return new Output().getOutput(name, elementValue.getElementValue());
	}

}
