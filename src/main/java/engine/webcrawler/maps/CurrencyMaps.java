package engine.webcrawler.maps;

import engine.webcrawler.InputData;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyMaps
{
	private ConcurrentHashMap<String, Double> fwdAnnualizeMap;
	private ConcurrentHashMap<String, Integer> tenordays;

	public CurrencyMaps(int type)
	{

		switch(type)
		{
//1 if we need the annualizer hashmap for tenors 1m-1y
			case 1 -> fwdAnnualizeMap();
//2 if we need approximate daycount for tenors 1m-1y
			case 2 -> tenorDays();
		}
	}

	private void fwdAnnualizeMap()
	{
		ConcurrentHashMap<String, Double> annualizers =new ConcurrentHashMap<>();

		annualizers.put("1M", (double) 1 / 30 * 360);
		annualizers.put("2M", (double) 1 / 60 * 360);
		annualizers.put("3M", (double) 1 / 90 * 360);
		annualizers.put("4M", (double) 1 / 120 * 360);
		annualizers.put("5M", (double) 1 / 150 * 360);
		annualizers.put("6M", (double) 1 / 180 * 360);
		annualizers.put("7M", (double) 1 /  210 * 360);
		annualizers.put("8M", (double) 1 /  240* 360);
		annualizers.put("9M", (double) 1 / 270 * 360);
		annualizers.put("10M", (double) 1 / 300 * 360);
		annualizers.put("11M", (double) 1 /  330 * 360);
		annualizers.put("1Y", (double) 1);

		this.fwdAnnualizeMap=annualizers;
	}

	private void tenorDays()
	{
		ConcurrentHashMap<String, Integer> tenordays =new ConcurrentHashMap<>();

		tenordays.put("1M", 30);
		tenordays.put("2M", 60);
		tenordays.put("3M",90);
		tenordays.put("4M",120);
		tenordays.put("5M", 150);
		tenordays.put("6M",180);
		tenordays.put("7M",210);
		tenordays.put("8M",240);
		tenordays.put("9M",270);
		tenordays.put("10M",300);
		tenordays.put("11M",330);
		tenordays.put("1Y",360);

		this.tenordays=tenordays;
	}

	public ConcurrentHashMap<String, Double> getFwdAnnualizeMap()
	{
		return fwdAnnualizeMap;
	}

	public ConcurrentHashMap<String, Integer> getTenordays()
	{
		return tenordays;
	}
}
