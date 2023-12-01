package tonystarkindustries.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import tonystarkindustries.TestComponents.BaseTest;
import tonystarkindustries.pageobjects.CartPage;
import tonystarkindustries.pageobjects.CheckoutPage;
import tonystarkindustries.pageobjects.ConfirmationPage;
import tonystarkindustries.pageobjects.LandingPage;
import tonystarkindustries.pageobjects.ProductCatalogue;

public class stepDefinitionImpl extends BaseTest{

	public LandingPage landingPage;      //we are able to access landing page obj from 2nd method also as we declared it globally here
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException {
		//This block runs when Background is called
		landingPage=launchApplication();
	}
	
	@Given("^Logged in with (.+) and (.+)$") //(^<statement>(.+)<statement>$) means we have a regular expression so we use regex(.+) and using ^,$ we state we have a regex
	public void Logged_in_username_and_password(String username, String password) {
		productCatalogue=landingPage.loginApplication(username,password);
	}
	
	@When ("^I want to add (.+) to Cart$")
	public void I_add_product_to_cart(String productName) throws InterruptedException {
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@And ("^Checkout (.+) and submit the order$")
	public void Checkout_submit_order(String productName) {
		CartPage cartPage=productCatalogue.goToCartPage();
		cartPage.getCartList();
		Boolean flag=cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(flag); 
		CheckoutPage checkOutPage=cartPage.goToCheckout();		
		checkOutPage.selectCountry("India");
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("scrollBy(0,350)", " ");
		confirmationPage=checkOutPage.submitOrder();
	}
	
	@Then ("{string} message is displayed on ConfirmationPage")        //FOr static arguments being passed in Cucumber
	public void message_displayed_confirmation_page(String string) {
		String confirmationMessage=confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase(string));
		driver.close();
	}
	
	@Then ("{string} message is displayed")
	public void message_is_displayed(String argument) {
		Assert.assertEquals(argument, landingPage.getErrorMessage());
		driver.close();
	}

}
