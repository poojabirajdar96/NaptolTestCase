package test;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.StaleElementReferenceException;
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
public class CartPageTest extends BaseTest{

	@BeforeMethod
	public void launchNaptol()
	{
		driver = Browser.openBrowser();				
	}
	
	@Test(priority = 1)
	public void verifyByAddingMultipleProductsToCart() throws EncryptedDocumentException, IOException, InterruptedException
	{
		test = reports.createTest("verifyByAddingMultipleProductsToCart");
		
		HomePage homePage = new HomePage(driver);
		homePage.enterValidProductName();
		homePage.clickOnSearchButton();
		homePage.moveToProduct(driver,1);
		homePage.clickOnQuickView(1);
		
		QuickViewPopup quickViewPopup = new QuickViewPopup(driver);
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
		Thread.sleep(2000);
		cartPage.clickOnCloseBtn();
		quickViewPopup.clickOnCloseBtn();
		Thread.sleep(2000);
		homePage.moveToProduct(driver, 2);
		homePage.clickOnQuickView(2);
		quickViewPopup.clickOnClickHereToBuyButton();
		Thread.sleep(2000);
		int count = cartPage.getCartItemCount();
		int itemCount = cartPage.getCartProductList();
		
		cartPage.enterQuantity(0);
		
		Assert.assertEquals(itemCount, count);
	}
	
	@Test(priority = 3)
	public void verifyByRemovingProductFromCart() throws EncryptedDocumentException, IOException, InterruptedException
	{
		test = reports.createTest("verifyByRemovingProductFromCart");
		
		HomePage homePage = new HomePage(driver);
		
		homePage.enterValidProductName();
		homePage.clickOnSearchButton();
		homePage.moveToProduct(driver,1);
		homePage.clickOnQuickView(1);
		
		QuickViewPopup quickViewPopup = new QuickViewPopup(driver);
		
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
		
		int beforeClickingOnRemoveProductCount= cartPage.getCartItemCount();

		String removedProductName = cartPage.clickOnRemoveBtn(0);
		
		Thread.sleep(3000);
		
		String[] cartProductsNamesList=cartPage.getCartProductNamesList();
		int afterClickingOnRemoveProductCount= cartPage.getCartItemCount();
		
		if(cartProductsNamesList.length>=0) {
			for(int i=0;i<cartProductsNamesList.length;i++) {
				if(removedProductName != cartProductsNamesList[i]) {
					removedProductName="";
					Assert.assertTrue(removedProductName.isEmpty());
				}
			}
		}
		
		Assert.assertTrue(beforeClickingOnRemoveProductCount>afterClickingOnRemoveProductCount);
		System.out.println("Assertion successful");
	}
	
	@Test(priority = 2)
	public void verifyOnChangingProductQuantityCorrectAmountIsDisplyed() throws EncryptedDocumentException, IOException, InterruptedException 
	{
		test = reports.createTest("verifyOnChangingProductQuantityCorrectAmountIsDisplyed");
		
        HomePage homePage = new HomePage(driver);
		
		homePage.enterValidProductName();
		homePage.clickOnSearchButton();
		homePage.moveToProduct(driver,1);
		homePage.clickOnQuickView(1);
		
		QuickViewPopup quickViewPopup = new QuickViewPopup(driver);
		
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
		cartPage.enterQuantity(0);
		Thread.sleep(2000);
		int quantity = cartPage.getProductQuantity(0);
		double unitPrice=cartPage.getProductUnitPrice(0);
		double shippingCharges = cartPage.getProductShippingCharge(0);
		double actualProductTotalAmount =cartPage.getProductTotalAmount(0);
		
        double expectedProductTotalAmount = (quantity * unitPrice) + shippingCharges;
		
		Assert.assertEquals(actualProductTotalAmount, expectedProductTotalAmount);
	}
	
	@Test(priority = 4)
	public void verifyTotalAmountForMultipleProductsInCart() throws EncryptedDocumentException, IOException, InterruptedException
	{
		test = reports.createTest("verifyTotalAmountForMultipleProductsInCart");
		HomePage homePage = new HomePage(driver);
		
		homePage.enterValidProductName();
		homePage.clickOnSearchButton();
		homePage.moveToProduct(driver,1);
		homePage.clickOnQuickView(1);
		
		QuickViewPopup quickViewPopup = new QuickViewPopup(driver);
		
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
		
		Thread.sleep(2000);
		cartPage.clickOnCloseBtn();
		quickViewPopup.clickOnCloseBtn();
		Thread.sleep(2000);
		homePage.moveToProduct(driver, 2);
		homePage.clickOnQuickView(2);
		quickViewPopup.clickOnClickHereToBuyButton();
		Thread.sleep(2000);
		
		cartPage.enterQuantity(0);
		
		double  giftVoucherDiscount=cartPage.getGiftVoucherDiscount();
		Thread.sleep(2000);
		double  actualTotalPayableCartAmount=cartPage.getTotalPayableCartAmount();
		
		double[] allProductTotalPriceList = cartPage.getAllProductTotalPriceList();
		double expectedTotalPayableCartAmount=0;
		
		for(int i=0;i<allProductTotalPriceList.length;i++) {
			expectedTotalPayableCartAmount=allProductTotalPriceList[i]+expectedTotalPayableCartAmount;
		}
		
		expectedTotalPayableCartAmount=expectedTotalPayableCartAmount - giftVoucherDiscount;
		
		Assert.assertEquals(actualTotalPayableCartAmount, expectedTotalPayableCartAmount);
		
	}
	
	@AfterMethod
	public void closeBrowser()
	{
		driver.close();
	}
}
