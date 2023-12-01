package tonystarkindustries.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.github.dockerjava.api.model.Driver;

import tonystarkindustries.AbstractComponents.AbstractComponent;
//Second Page
public class ProductCatalogue extends AbstractComponent{
	
	WebDriver driver; //This 'driver' is a Class variable
	public ProductCatalogue(WebDriver driver) {//This driver is an instance variable in a Constructor and the scope is limited to this function
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);//this is imp for @FindBy to get the driver and 'this' refers to current class driver
		//We write this inside of Constructor because Constructor gets executed before anything else 
	}
	//List<WebElement> products=driver.findElements(By.cssSelector("[class*='mb-3']"));
	@FindBy(css="[class*='mb-3']")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement spinnerAnimation;
	
	By productsBy=By.cssSelector("[class*='mb-3']");
	By addToCart=By.cssSelector("[class='btn w-10 rounded']");
	By productAddedToCartToastMessage=By.cssSelector("[aria-label='Product Added To Cart']");
	
	public  List<WebElement> getProductList() {
		//Before getting the list of webelements we need to wait for the loading screen to end
		//for that we already made the method in AbstractComponent called waitforelementtoappear
		waitForElementToAppear(productsBy);
		return  products;
	}
	
	public WebElement getProductByName(String productName) {
		WebElement prod=getProductList().stream().filter(i->
		i.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}
	
	public void addProductToCart(String productName) throws InterruptedException {
		WebElement prod= getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToAppear(productAddedToCartToastMessage);
		Thread.sleep(2000);//use this instead of the below due to site issue
		//waitForElementToDisappear(spinnerAnimation);
	}
	
}
