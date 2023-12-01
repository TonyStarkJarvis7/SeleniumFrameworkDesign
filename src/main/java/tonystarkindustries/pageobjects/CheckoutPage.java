package tonystarkindustries.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import tonystarkindustries.AbstractComponents.AbstractComponent;
//Fourth Page
public class CheckoutPage extends AbstractComponent{
	
	WebDriver driver;
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(css="[placeholder='Select Country']")
	private WebElement countryDropdown;	//Since we do not want these elements to be called  from other classeses,we mark them private
	//We are hiding fields and only exposing action methods and achieving encapsulation
	/*WebElement countryDropdown=driver.findElement(By.cssSelector("[placeholder='Select Country']"));
	countryDropdown.sendKeys("india");*/
	
	@FindBy(css="[class*='ta-item']")
	private List<WebElement> selectCountry;
	/*List<WebElement> dd=driver.findElements(By.cssSelector("[class*='ta-item']"));
	dd.stream().filter(i->i.getText().equalsIgnoreCase("India")).forEach(i->i.click());*/
	
	@FindBy(css="[class*='action__submit ']")
	private WebElement submit;
	//driver.findElement(By.cssSelector("[class*='action__submit ']")).click();
	
	private By countryList=By.cssSelector("section[class*='ta-results']"); //this is not run through driver
	
	public void selectCountry(String country) {
		countryDropdown.sendKeys(country);
		waitForElementToAppear(countryList);
		selectCountry.stream().filter(i->i.getText().equalsIgnoreCase(country)).forEach(i->i.click());
		
	}
	
	public ConfirmationPage submitOrder() {
		waitForElementToBeClickable(submit);
		submit.click();
		//ConfirmationPage confirmationPage=new ConfirmationPage(driver);
		return new ConfirmationPage(driver);
	}
	
	
}
