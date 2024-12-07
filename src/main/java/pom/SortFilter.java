package pom;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import utility.Parameterization;

public class SortFilter extends CommonMethods{
	
	@FindBy(xpath = "//select[@id='sortByFilter']") private WebElement SortFilterSelectBox;
	@FindBy(xpath = "//select[@id='sortByFilter']//option")private List<WebElement> SortOptionList;
	@FindBy(xpath = "//span[@class='offer-price']")private List<WebElement> ProductPriceList;
	@FindBy(xpath = "//div[@class='item_title']") private List <WebElement> ProductsNameList;
	
	public SortFilter(WebDriver driver)
	{
		PageFactory.initElements(driver, this);	
	}
	
	public void clickOnSortFilter() {
		SortFilterSelectBox.click();
	}
	
	public void getSelectedOptionFromList(int row) throws EncryptedDocumentException, IOException {
		Select option = new Select(SortFilterSelectBox);
		option.selectByVisibleText(Parameterization.getStringDataFromSheet("SortOptionList", row, 0));
	}
	
	public double[] getProductsPriceList() {
		double[] priceList = new double[ProductPriceList.size()];
			
		for(int i=0;i<ProductPriceList.size();i++) {
			String[] price= ProductPriceList.get(i).getText().split(" ");
			priceList[i] = Double.parseDouble(removeCommaFromString(price[0]));
		}
		
		return priceList;
	}
	
	public String[] getProductsNameList() {
		String[] nameList = new String[ProductsNameList.size()];
		
		for(int i=0;i<ProductsNameList.size();i++) {
			nameList[i] =ProductsNameList.get(i).getText();
		}
		
		return nameList;	
	}
}
