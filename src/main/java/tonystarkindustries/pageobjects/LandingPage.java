package tonystarkindustries.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.github.dockerjava.api.model.Driver;

import tonystarkindustries.AbstractComponents.AbstractComponent;
//First Page
public class LandingPage extends AbstractComponent{
	
	WebDriver driver; //This 'driver' is a Class variable
	public LandingPage(WebDriver driver) {//This driver is an instance variable and the scope is limited to this function
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);//this is imp for @FindBy to get the driver and 'this' refers to current class driver
		//We write this inside of Constructor because Constructor gets executed before anything else 
	}
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement passwordEle;
	
	@FindBy(id="login")
	WebElement submit;
	
	@FindBy(css="div[aria-label='Incorrect email or password.']")
	WebElement errorMessage;
	
	/*
	 * driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("tonystark@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Cognizant1*");
		driver.findElement(By.id("login")).click();
	 */
	//WebDriver driver;//When 
	//WebElement userEmail=driver.findElement(By.id("userEmail"));
	//When initialized this way, the driver here has no knowledge that the driver we are talking about
	//is about the driver which is in 'StandAloneTest'. Writing such long statement is also difficult.
	/*
	 * To avoid that we will be calling a constructor and as we know that constructor is the first method which
	 * gets executed. when this method is called through obj initialization, We will create it to get the actual
	 * driver from 'StandAloneTest' to this java class through 'this' keyword and store the value from instance
	 * variable to class variable
	 * 
	 * There is an other way of writing the code by writing @FindBy. This @FindBy mimics the complete statement
	 * driver.findElement(By.id("")); and we will just have to pass the tag inside it.
	 * Example @FindBy(xpath="<give the xpath>")
	 * 		   WebElement <variable name>
	 *  
	 */
	
	public void goTo() {
		driver.manage().deleteAllCookies();
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public ProductCatalogue loginApplication(String email, String password) {
		userEmail.sendKeys(email);
		passwordEle.sendKeys(password);
		submit.click();
		ProductCatalogue productCatalogue=new ProductCatalogue(driver);
		return productCatalogue;
		
	}
	
	public String getErrorMessage() {
		waitforWebElementToAppear(errorMessage);
		return errorMessage.getText();
		
	}
	
}
