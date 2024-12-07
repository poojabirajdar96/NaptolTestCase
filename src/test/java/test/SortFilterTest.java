package test;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import junit.framework.Assert;
import pojo.Browser;
import pom.HomePage;
import pom.SortFilter;

@Listeners(test.Listeners.class)
public class SortFilterTest extends BaseTest {
	
	@BeforeMethod
	public void launchNaptol()
	{
		driver = Browser.openBrowser();				
	}
	
	@Test
	public void verifySortFeature() throws EncryptedDocumentException, IOException, InterruptedException 
	{
		test = reports.createTest("verifySortFeature");
		
		HomePage homePage = new HomePage(driver);
		homePage.enterValidProductName();
		homePage.clickOnSearchButton();
		Thread.sleep(3000);
		String[] beforeSortProductNameList = homePage.getProductsNameList();
		
		SortFilter sortFilter= new SortFilter(driver);
		
		//sortFilter.clickOnSortFilter();
		sortFilter.getSelectedOptionFromList(3);
		Thread.sleep(3000);
		double productPriceList[]=sortFilter.getProductsPriceList();

		for(int i=0;i<productPriceList.length;i++) {
			if(i<productPriceList.length-1)
			  Assert.assertTrue(productPriceList[i]>=productPriceList[i+1]);
		}
		
		Thread.sleep(3000);
		
		sortFilter.getSelectedOptionFromList(4);
		
		for(int i=productPriceList.length-1;i>=0;i--) {
			if(i>0)
			Assert.assertTrue(productPriceList[i]<=productPriceList[i-1]);
		}
		
		sortFilter.getSelectedOptionFromList(0);
		Thread.sleep(3000);
		String mostPopularSortProductNameList[]=sortFilter.getProductsNameList();
		for(int i =0; i<mostPopularSortProductNameList.length;i++) {
			Assert.assertTrue(beforeSortProductNameList[i]!=mostPopularSortProductNameList[i]);
		}
		
		sortFilter.getSelectedOptionFromList(1);
		Thread.sleep(3000);
		String newArrivalSortProductNameList[]=sortFilter.getProductsNameList();
		for(int i =0; i<newArrivalSortProductNameList.length;i++) {
			Assert.assertTrue(beforeSortProductNameList[i]!=newArrivalSortProductNameList[i]);
		}
	}
	
	@AfterMethod
	public void closeBrowser()
	{
		driver.close();
	}
}
