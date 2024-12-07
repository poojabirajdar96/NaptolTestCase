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

@Listeners(test.Listeners.class)
public class HomePageTest extends BaseTest{

	@BeforeMethod
	public void launchNaptol()
	{
		driver = Browser.openBrowser();				
	}
	
	@Test(priority = 1)
	public void searchAValidProduct() throws EncryptedDocumentException, IOException
	{
		test = reports.createTest("searchAValidProduct");
		HomePage homePage = new HomePage(driver);
		homePage.enterValidProductName();
		homePage.clickOnSearchButton();
		String searchResult = homePage.getSearchResult();	
		double searchCount = Double.parseDouble(searchResult)	;
		Assert.assertTrue(searchCount>0);
	}
	
	@Test(priority = 2)
	public void searchInvalidProduct() throws EncryptedDocumentException, IOException
	{
		test = reports.createTest("searchInvalidProduct");
		HomePage homePage = new HomePage(driver);
		homePage.enterInvalidProductName();
		homePage.clickOnSearchButton();
		String searchResult = homePage.getSearchResult();
		double searchCount = Double.parseDouble(searchResult);
		Assert.assertTrue(searchCount==0);	
	}
	
	@Test(priority = 3)
	public void verifyShoppingCategoriesDropdownList()
	{
		test = reports.createTest("verifyShoppingCategoriesDropdownList");
		HomePage homePage = new HomePage(driver);
		homePage.mouseHoverOnShoppingCategories(driver);
		Assert.assertTrue(homePage.verifyShoppingcategoriesDropdownList());
	}
	
	@AfterMethod
	public void closeBrowser()
	{
		driver.close();
	}
}
