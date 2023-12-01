package tonystarkindustries.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import tonystarkindustries.AbstractComponents.AbstractComponent;

public class ConfirmationPage extends AbstractComponent{

	WebDriver driver;
	public ConfirmationPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css="[class='hero-primary']")
	WebElement confirmationMessage;
	
	public String getConfirmationMessage() {//PageObject file is dedicated only for actions and any assertions needs to be done in tests only
		return confirmationMessage.getText();
	}
	
}
