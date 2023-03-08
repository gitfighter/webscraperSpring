/** currently not used */

package engine.webcrawler.enummaps;
import engine.webcrawler.enums.*;

import java.util.EnumMap;

public class CurrencyMaps
{
	public EnumMap<CurrencyFWDTenors, Integer> tenordays()
	{
		EnumMap<CurrencyFWDTenors, Integer> tenordays360 = new EnumMap<CurrencyFWDTenors, Integer>(CurrencyFWDTenors.class);

		tenordays360.put(CurrencyFWDTenors.FWD_1M  , 30);
		tenordays360.put(CurrencyFWDTenors.FWD_2M  , 60);
		tenordays360.put(CurrencyFWDTenors.FWD_3M  , 90);
		tenordays360.put(CurrencyFWDTenors.FWD_4M  , 120);
		tenordays360.put(CurrencyFWDTenors.FWD_5M  , 150);
		tenordays360.put(CurrencyFWDTenors.FWD_6M  , 180);
		tenordays360.put(CurrencyFWDTenors.FWD_9M  , 270);
		tenordays360.put(CurrencyFWDTenors.FWD_1Y,  360);
		tenordays360.put(CurrencyFWDTenors.FWD_2Y,  720);
		tenordays360.put(CurrencyFWDTenors.FWD_3Y,  1080);
		tenordays360.put(CurrencyFWDTenors.FWD_5Y,  1800);
		tenordays360.put(CurrencyFWDTenors.FWD_10Y,  3600);

		return tenordays360;
	}



}
