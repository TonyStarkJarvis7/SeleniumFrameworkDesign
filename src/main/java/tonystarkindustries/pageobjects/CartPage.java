package tonystarkindustries.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import tonystarkindustries.AbstractComponents.AbstractComponent;
//Third Page
public class CartPage extends AbstractComponent{

	WebDriver driver;
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[class='cartSection']")
	List<WebElement> cartItems;
	
	@FindBy(xpath="//button[contains(text(),'Checkout')]")
	WebElement checkOutEle;
	
	public List<WebElement> getCartList() {
		waitForElementToAppear(By.cssSelector("[class='cartSection']"));
		List<WebElement> cartItems= driver.findElements(By.cssSelector("[class='cartSection']"));
		return cartItems;
	}
	
	public boolean verifyProductDisplay(String productName) {
		boolean flag=getCartList().stream().anyMatch(i->i.findElement(By.xpath("//div[@class='cartSection']//h3")).getText().equalsIgnoreCase(productName));
		return flag;
	}
	
	public CheckoutPage goToCheckout() {
		checkOutEle.click();
		//CheckoutPage checkOutPage = new CheckoutPage(driver);
		return new CheckoutPage(driver); //A little different way of writing it
	}
	
}
