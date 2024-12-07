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
import pom.ProductDetailsPage;

@Listeners(test.Listeners.class)
public class ProductDetailsPageTest extends BaseTest{

	@BeforeMethod
	public void launchNaptol()
	{
		driver = Browser.openBrowser();				
	}
	
	@Test(priority = 1)
	public void verifyProductDetailsAreCorrectIfViewInNewTab() throws EncryptedDocumentException, IOException
	{
		test = reports.createTest("verifyProductDetailsAreCorrectIfViewInNewTab");
		
		HomePage homePage = new HomePage(driver);
		homePage.enterValidProductName();
		homePage.clickOnSearchButton();
		
		String homePageProductName =  homePage.getProductName(1);
		Double homePageProductPrice = homePage.getProductPrice(1);
		
		homePage.clickOnProductDiv(driver);
		
		ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
		
		String childBrowserProductName = productDetailsPage.getProductName();
		double childBrowserProductPrice = productDetailsPage.getProductPrice(1);
		
		Assert.assertEquals(homePageProductName , childBrowserProductName);
		Assert.assertEquals(homePageProductPrice, childBrowserProductPrice);
	}
	
	@Test(priority = 2)
	public void verifyByAddingProductToCartUsingProductDetailPage() throws EncryptedDocumentException, IOException
	{
		test = reports.createTest("verifyByAddingProductToCartUsingProductDetailPage");
		HomePage homePage = new HomePage(driver);
		
		homePage.enterValidProductName();
		homePage.clickOnSearchButton();
		homePage.clickOnProductDiv(driver);
		
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
		
		String childBrowserProductName = productDetailsPage.getProductName();
		
		if(productDetailsPage.getProductColorList()>0)
		{
			productDetailsPage.selectProductColor(1);			
			productDetailsPage.clickOnClickHereToBuyButton();			
		}
		else
		{
			productDetailsPage.clickOnClickHereToBuyButton();
		}
		
		CartPage cartPage = new CartPage(driver);
		
        String cartPageProductName = cartPage.getCartProductname();
		
		Assert.assertEquals(childBrowserProductName,cartPageProductName);
	}
	
	@AfterMethod
	public void closeBrowser()
	{
		driver.close();
	}
}
