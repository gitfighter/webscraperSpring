/** main class for instructions */

package engine.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

//csv info bepakolasa a krealt 2d arraybol, formatalt eredmenyek kidobbasa az outputValues arraylistbe
			for(int j=0;j< inputData.length;j++)
			{
				outputValues.add(elementPipeline(inputData[j][0], inputData[j][1], inputData[j][2]));
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(314,778)); //pusztamelleki antibotprotekcio
			}

//arraylist kiirasa fileba
			output.arraylistToFile("output.txt", outputValues);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	protected static String elementPipeline(String name, String element, String url)
	{
//#1 LETOLTI
		var Page = new WebCrawler(url);
//#2 ELEMENT VALUET EXTRACTOLJA
		var Element=new ValueExtractor(Page, element,url);
//#3 FORMAZOTT STRINGET RETURNOLI (amit berak a mai a stringarraylistba)
		return new Output().getOutput(name, Element.getElementValue());
	}

}
