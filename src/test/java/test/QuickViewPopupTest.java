package test;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import junit.framework.Assert;
import pojo.Browser;
import pom.CartPage;
import pom.HomePage;
import pom.QuickViewPopup;

@Listeners(test.Listeners.class)
public class QuickViewPopupTest extends BaseTest {

	@BeforeMethod
	public void launchNaptol()
	{
		driver = Browser.openBrowser();				
	}
	
	@Test(priority = 1)
	public void verifyIfProdutDetailsAreCorrcetIfViewInQuickView() throws EncryptedDocumentException, IOException 
	{
		test = reports.createTest("verifyIfProdutDetailsAreCorrcetIfViewInQuickView");
		
		HomePage homePage = new HomePage(driver);
		homePage.enterValidProductName();
		homePage.clickOnSearchButton();
		homePage.moveToProduct(driver,1);	
		
		boolean verifyQuickViewPopupDisplayedOrNot = homePage.clickOnQuickView(1);	
		
		Assert.assertTrue(verifyQuickViewPopupDisplayedOrNot);
		
		String homePageProductName =  homePage.getProductName(1);
		Double homePageProductPrice = homePage.getProductPrice(1);
		
		QuickViewPopup quickViewPopup = new QuickViewPopup(driver);
		
		String quickViewProductName = quickViewPopup.getProductName();	
		Double quickViewProductPrice = quickViewPopup.getProductPrice(1);
		
		Assert.assertEquals(homePageProductName, quickViewProductName);		
		
		Assert.assertEquals(homePageProductPrice, quickViewProductPrice);				
	}
	
	@Test(priority = 2)
	public void verifyByAddingProductToCartUsingQuickView() throws EncryptedDocumentException, IOException, InterruptedException
	{
		test = reports.createTest("verifyByAddingProductToCartUsingQuickView");
		
		HomePage homePage = new HomePage(driver);
		homePage.enterValidProductName();
		homePage.clickOnSearchButton();
		homePage.moveToProduct(driver,1);
		homePage.clickOnQuickView(1);
		
		QuickViewPopup quickViewPopup = new QuickViewPopup(driver);
		String quickViewProductName = quickViewPopup.getProductName();
		
		if(quickViewPopup.getProductColorList()>0)
		{
			quickViewPopup.selectProductColor(1);			
			quickViewPopup.clickOnClickHereToBuyButton();			
		}
		else
		{
			quickViewPopup.clickOnClickHereToBuyButton();
		}
		
		CartPage cartPage = new CartPage(driver);
		
        String cartPageProductName = cartPage.getCartProductname();
		
		Assert.assertEquals(quickViewProductName,cartPageProductName);
	}
	
	@AfterMethod
	public void closeBrowser()
	{
		driver.close();
	}
}
	
