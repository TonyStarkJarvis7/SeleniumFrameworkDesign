package tonystarkindustries.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import tonystarkindustries.pageobjects.CartPage;
//Reusable Action Methods
public class AbstractComponent {

	WebDriver driver;
	
	public AbstractComponent(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css="[routerlink='/dashboard/cart']")
	WebElement cartHeader;
	
	@FindBy(css="button[routerlink='/dashboard/myorders']")
	WebElement orderHeader;
	
	public void waitForElementToAppear(By locator) {//By is the return Type of Locator(Example: By.<locatorpart>)
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void waitforWebElementToAppear(WebElement locator) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(2));
		wait.until(ExpectedConditions.visibilityOf(locator));
	}
	
	public void waitForElementToDisappear(WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(ele));
	}
	
	public CartPage goToCartPage() {
		cartHeader.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
	
	public OrdersPage goToOrdersPage() {
		orderHeader.click();
		OrdersPage ordersPage=new OrdersPage(driver);
		return ordersPage;
	}
	
	public void waitForElementToBeClickable(WebElement submit) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(2));
		wait.until(ExpectedConditions.elementToBeClickable(submit));
	}
	
	
}
