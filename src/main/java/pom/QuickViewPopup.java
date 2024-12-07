package pom;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class QuickViewPopup extends CommonMethods {
	
	@FindBy(xpath = "//div[@id='square_Details']//h1") private WebElement ProductName;
	@FindBy(xpath = "//span[@class='offer-price']") private List<WebElement> ProductsPriceList;
	
	@FindBy(xpath = "//ul[@class='sizeBox clearfix']//li") private List<WebElement> ProductColorList;
	
	@FindBy(xpath = "//a[@id='cart-panel-button-0']") private WebElement ClickHereToBuyButton;
	
	@FindBy(xpath = "//div[@id='QuickView']//button")private WebElement CloseBtn;
	
	public QuickViewPopup(WebDriver driver)
	{
		PageFactory.initElements(driver, this);	
	}
	
	public String getProductName()
	{
		return ProductName.getText();
	}
		
	public double getProductPrice(int index)
	{
		String [] a = ProductsPriceList.get(index).getText().split(" ");
		return Double.parseDouble(removeCommaFromString(a[0]));	
	}
	
	public void selectProductColor(int index)
	{				
		ProductColorList.get(index).click();	
	}
	
	public int getProductColorList()
	{
		return ProductColorList.size();
	}
	
	public void clickOnClickHereToBuyButton()
	{
		ClickHereToBuyButton.click();	
	}
	
	public void clickOnCloseBtn() {
		CloseBtn.click();
	}
	
}
