/** useful functions - not in use yet */

package engine.webcrawler;
public class Util
{
	// selenium webelement list to processable table (arraylist<String[]>)
	/*
				List<WebElement> rows = fwdPriceElement.findElements(By.tagName("tr"));

//split rows into String[] put in arraylist creating a 2d arraylist/array
			String[] rowsFinal = new String[rows.size()];
			ArrayList<String[]> fwdTable = new ArrayList<>();

			for (int i = 1; i < rows.size(); i++)
			{
//convert from WebElement to String[]
				rowsFinal[i] = rows.get(i).getText();
//split to String[] put in table
				fwdTable.add(rowsFinal[i].split(" "));
			}
			driver.quit();

			this.forwardTable=fwdTable;
	 */

	//selenium webelement list to String[]
	/*
		public String[] seleniumWebElementListToStringArray(List<WebElement> webelement)
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
	 */


}
