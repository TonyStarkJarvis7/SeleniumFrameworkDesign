package tonystarkindustries.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import tonystarkindustries.AbstractComponents.AbstractComponent;
import tonystarkindustries.TestComponents.BaseTest;
import tonystarkindustries.TestComponents.Retry;
import tonystarkindustries.pageobjects.CartPage;
import tonystarkindustries.pageobjects.CheckoutPage;
import tonystarkindustries.pageobjects.ConfirmationPage;
import tonystarkindustries.pageobjects.LandingPage;
import tonystarkindustries.pageobjects.ProductCatalogue;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ErrorValidationsTest extends BaseTest{
//All negative error validations are wrapped here
	@Test (groups={"ErrorHandling"},retryAnalyzer=Retry.class) //Retry.class which was being used in the videos works after importing the package
	public void LoginErrorValidation() throws InterruptedException, IOException { 
		// TODO Auto-generated method stub
		//String URL="https://rahulshettyacademy.com/client"; URL is already present in GLobal level in goTo fun
		
		ProductCatalogue productCatalogue=landingPage.loginApplication("tonys6tark@gmail.com", "Cogn4izant1*");//PAssing values to login
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		
	}
	
	@Test(retryAnalyzer=Retry.class)
	public void ProductErrorValidation() throws InterruptedException, IOException { 
		//String URL="https://rahulshettyacademy.com/client"; URL is already present in GLobal level in goTo fun
		String prdtName="ZARA COAT 3",cardMonth="12",cardYear="23", countryName="India";
		ProductCatalogue productCatalogue=landingPage.loginApplication("tonystark@gmail.com", "Cognizant1*");//PAssing values to login
	
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(prdtName);
		
		CartPage cartPage=productCatalogue.goToCartPage();
		cartPage.getCartList();
		Boolean flag=cartPage.verifyProductDisplay(prdtName+"33");
		Assert.assertFalse(flag); 
		
		
	}
	
	
}
