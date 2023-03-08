package engine.webcrawler;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ForwardCalculations
{
	private ArrayList<String> tenor;
	private ArrayList<Double> bidfwdPercent;
	private ArrayList<Double> offerfwdPercent;
	private ArrayList<String[]> forwardTable=null;
	private Double spot=0.0;
	private String tickername;

	public ForwardCalculations() //to reach formatFwdTable - any problems later?
	{
	}

	public ForwardCalculations(String name, String url, String element)
	{
		var fwdTable=new ForwardCrawler(url, element);
		this.forwardTable = fwdTable.getForwardTable();
		this.spot= fwdTable.getSpot();
		this.tickername=name;
		roughFwdPercent();
	}

	private void roughFwdPercent()
	{
		try
		{

			//split table into arraylists (column0>>> tenor, column1>>> bid, column2>>> offer)
			ArrayList<String> tenor = new ArrayList<>();
			ArrayList<Double> bid = new ArrayList<>();
			ArrayList<Double> offer = new ArrayList<>();

			for (String[] arrayelements : this.forwardTable)
			{
				tenor.add(arrayelements[1]);
				bid.add(Double.parseDouble(arrayelements[3]));
				offer.add(Double.parseDouble(arrayelements[4]));
			}

			ArrayList<Double> bidFwdPercentNotannualized = new ArrayList<>();
			ArrayList<Double> offerFwdPercentNotannualized = new ArrayList<>();

//calculate annualized fwd yields for CHFHUF price format currencies
			for (int i = 0; i < bid.size(); i++)
			{
				bidFwdPercentNotannualized.add((spot + bid.get(i) / 100) / spot - 1);
				offerFwdPercentNotannualized.add((spot + offer.get(i) / 100) / spot - 1);
			}
			//rough annualizer array for 1m+
			ArrayList<Double> annualizer = new ArrayList<>();
			for (int i = 0; i < 6; i++)
			{
				annualizer.add(1.0);
			}
			annualizer.add((double) 1 / 30 * 360);
			annualizer.add((double) 1 / 60 * 360);
			annualizer.add((double) 1 / 90 * 360);
			annualizer.add((double) 1 / 120 * 360);
			annualizer.add((double) 1 / 150 * 360);
			annualizer.add((double) 1 / 180 * 360);
			annualizer.add((double) 1 / 270 * 360);
			annualizer.add((double) 1);
			annualizer.add((double) 1 / 2);
			annualizer.add((double) 1 / 3);
			annualizer.add((double) 1 / 5);
			annualizer.add((double) 1 / 10);

			//roughly annualized 1m+ bid/offer
			ArrayList<Double> bidFwdPercent = new ArrayList<>();
			ArrayList<Double> offerFwdPercent = new ArrayList<>();

			for (int i = 0; i < bid.size(); i++)
			{
				bidFwdPercent.add(bidFwdPercentNotannualized.get(i) * annualizer.get(i) * 100);
				offerFwdPercent.add(offerFwdPercentNotannualized.get(i) * annualizer.get(i) * 100);
			}

			this.bidfwdPercent=bidFwdPercent;
			this.offerfwdPercent=offerFwdPercent;
			this.tenor=tenor;
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public ArrayList<Double> getBidfwdPercent()
	{
		return bidfwdPercent;
	}

	public ArrayList<Double> getOfferfwdPercent()
	{
		return offerfwdPercent;
	}

	public ArrayList<String> getTenor()
	{
		return tenor;
	}

	protected String[] formatFwdTable()
	{
		String[] fwdOutput=new String[tenor.size()];

		for (int i=0;i<(tenor.size());i++ )
		{
			fwdOutput[i]=(
					this.tickername+
					" "+
					this.tenor.get(i)+
					" "+
					String.format("%,.2f", this.bidfwdPercent.get(i))+
					"% - "+
					String.format("%,.2f", this.offerfwdPercent.get(i))+
					"% "+
					"::Last Update: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n");
		}

		return fwdOutput;
	}
}
