package pom;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;

public class CommonMethods {

	public String removeCommaFromString(String s)
	{
		String newString = "";
		for(int i=0;i<s.length();i++)
		{
			if(s.charAt(i)!=',')
			{
				newString = newString + s.charAt(i);
			}
		}		
		return newString;	
	}
	
	public String removeBracesFromString(String s)
	{
		String newString = "";
		
		for(int i=0;i<s.length();i++)
		{	
			if(s.charAt(i)!='(' && s.charAt(i)!=')')
			{	
				newString = newString+s.charAt(i);	
			}
		}		
		return newString;	
	}
	
	
	public void launchChildBrowser(WebDriver driver)
	{
		Set<String> handles =driver.getWindowHandles();		
		Iterator<String> i=handles.iterator();
		
		while(i.hasNext())
		{
			driver.switchTo().window(i.next());
		}
	}

}
