package pom;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utility.MouseActions;
import utility.Parameterization;

public class HomePage extends CommonMethods {

	@FindBy(xpath = "//input[@id='header_search_text']") private WebElement SearchInputBox;
	@FindBy(xpath = "(//a[@href='javascript:autoSuggestion.headerSearch()'])[2]")private WebElement SearchButton;
	@FindBy(xpath = "//span[@id='resultCountSpan']") private WebElement SearchResultCount;
	
	@FindBy(xpath = "//div[@class='cate_head']")private WebElement ShoppingCategoriesBtn; 
	@FindBy(xpath = "//div[@id='mainMenuContent']") private WebElement ShoppingCategoriesDropdownList;
	
	@FindBy(xpath = "//div[@class='item_title']") private List <WebElement> ProductsNameList;
	@FindBy(xpath = "//span[@class='offer-price']") private List<WebElement> ProductsPriceList;
	
	@FindBy(xpath = "//a[@class='bt_compare icon chat quickFancyBox']") private List <WebElement> QuickViewBtn;
	
	@FindBy(xpath = "//div[@id='productItem2']") private WebElement HomePageProductDetailsDiv;
	
	@FindBy(xpath = "//section[@id='quickViewBox']") private WebElement QuickViewProductDetailsPopUp;

	public HomePage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public void enterValidProductName() throws EncryptedDocumentException, IOException
	{
		SearchInputBox.sendKeys(Parameterization.getStringDataFromSheet("TestData", 0, 0));		
	}
	
	public void enterInvalidProductName() throws EncryptedDocumentException, IOException
	{
		SearchInputBox.sendKeys(Parameterization.getStringDataFromSheet("TestData", 1, 0));	
	}
	
	public void clickOnSearchButton()
	{
		SearchButton.click();
	}
	
	public String getSearchResult()
	{
		return SearchResultCount.getText();
	}
	
	public void mouseHoverOnShoppingCategories(WebDriver driver)
	{			
		MouseActions.mouseHoverAction(driver,ShoppingCategoriesBtn);	
	}
	
	public boolean verifyShoppingcategoriesDropdownList()
	{
		return ShoppingCategoriesDropdownList.isDisplayed(); 
	}
	
	public void moveToProduct(WebDriver driver, int index)
	{
		MouseActions.mouseHoverAction(driver,ProductsNameList.get(index));
	}
	
	public String getProductName(int index)
	{
		String productName = ProductsNameList.get(index).getText();
		return productName;
	}
	
	public String[] getProductsNameList() {
		String[] nameList = new String[ProductsNameList.size()];
		for(int i=0;i<ProductsNameList.size();i++) {
			nameList[i] =ProductsNameList.get(i).getText();
		}
		
		return nameList;
		
	}
	
	public double getProductPrice(int index)
	{
		String [] a = ProductsPriceList.get(index).getText().split(" ");
		return Double.parseDouble(removeCommaFromString(a[0]));	
	}
	
	public boolean clickOnQuickView(int index)
	{
		QuickViewBtn.get(index).click();
		return QuickViewProductDetailsPopUp.isDisplayed();	
	}
	
	public void clickOnProductDiv(WebDriver driver)
	{
		HomePageProductDetailsDiv.click();
		launchChildBrowser(driver);	
	}
}
