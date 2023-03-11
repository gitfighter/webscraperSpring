package engine.webcrawler;

import java.util.ArrayList;
//will route input and output too?
public class Router
{
	public Router()
	{
		try
		{
//reads tickers&required data to process from file
			var lpInputDummy=new InputData("lastpricelist.csv", 3);
			final String[][] lpInputData=lpInputDummy.getInputElements();
			var fwdInputDummy=new InputData("fwdpricelist.csv", 3); //,, used in csv for (stupid?) convention
			final String[][] fwdInputData=fwdInputDummy.getInputElements();

//for accessing functions in Output, amateur
			ArrayList<String> lpOutputValues=new ArrayList<>();
			ArrayList<String[]> fwdOutputValues=new ArrayList<>();


//puts fwd price inputdata into download&processing pipeline /CALLS FUNCTIONS IN CONSTRUCTOR/
			for(int j=0;j< fwdInputData.length;j++)
			{
				fwdOutputValues.add(fwdElementPipeline(
						fwdInputData[j][0],
						fwdInputData[j][1],
						fwdInputData[j][2]));
			}

//also adds processed last prices to arraylist for printing to file
			for(int j=0;j< lpInputData.length;j++)
			{
				lpOutputValues.add(lpElementPipeline(
						lpInputData[j][0],
						lpInputData[j][1],
						lpInputData[j][2]
				));
			}


//writes last prices then fwdtables to files
			var output=new Output();
			output.arraylistToFile("output.txt", lpOutputValues);

			output.fwdtableToFile("fwdoutput.txt",fwdOutputValues);

			System.out.println("Processing done, have a nice day!");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static String lpElementPipeline(String name, String element, String url) throws InterruptedException
	{
//constuctor starts processing input sent by Router, calls functions
		var lpValue = new LastpriceCrawler(name, url, element);

		return new Output().formatLastPrice(name, lpValue.getElementValue());

	}

	private static String[] fwdElementPipeline(String name, String element, String url)
	{
//constuctor starts processing input sent by Router, calls functions
		var fwdTable = new ForwardCalculations(name, url, element);
		return fwdTable.formatFwdTable(); //return rough annualized figures between 1m-1y
	}
}