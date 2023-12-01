package tonystarkindustries.AbstractComponents;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrdersPage extends AbstractComponent{

	WebDriver driver;
	public OrdersPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//tr[@class='ng-star-inserted']/td[2]")
	List<WebElement> orderNames;
	
	public boolean verifyOrdersDisplay(String productName) {
		boolean match=orderNames.stream().anyMatch(i->i.getText().equalsIgnoreCase(productName));
		return match;
	}
	
}
