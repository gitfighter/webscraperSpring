package engine.webcrawler;

import engine.webcrawler.maps.CurrencyMaps;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

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

//deciding if we need to divide the foward points by 100 or 10000
			int divider=0;

				if(
						tickername.contains("JPY") ||
						tickername.contains("HUF") ||
						tickername.contains("ISK") ||
						tickername.contains("RSD") || //not sure but probably from here
						tickername.contains("KES") ||
						tickername.contains("IDR") ||
						tickername.contains("NGN") ||
						tickername.contains("UYU"))
				{
					divider=100;
				}
				else divider=10000;

//calculate annualized fwd yields

			for (int i = 0; i < bid.size(); i++)
			{
				bidFwdPercentNotannualized.add((spot + bid.get(i) / divider) / spot - 1);
				offerFwdPercentNotannualized.add((spot + offer.get(i) / divider) / spot - 1);
			}
//rough annualizer array for 1m+
//constructor starts fwdAnnualizeMap() which creates concurrent hashmap for annualizing tenor yields
			CurrencyMaps annualizers=new CurrencyMaps(1); //2: annualizer map
			ArrayList<Double> annualizer = new ArrayList<>();


			for (int i = 0; i < tenor.size(); i++)
			{
				annualizer.add(annualizers.getFwdAnnualizeMap().getOrDefault(tenor.get(i), 0.0));
			}

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

	protected ArrayList<Double> getBidfwdPercent()
	{
		return bidfwdPercent;
	}

	protected ArrayList<Double> getOfferfwdPercent()
	{
		return offerfwdPercent;
	}

	protected ArrayList<String> getTenor()
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
