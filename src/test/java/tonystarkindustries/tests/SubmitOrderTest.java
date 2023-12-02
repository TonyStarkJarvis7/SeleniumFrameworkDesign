package tonystarkindustries.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import tonystarkindustries.AbstractComponents.AbstractComponent;
import tonystarkindustries.AbstractComponents.OrdersPage;
import tonystarkindustries.TestComponents.BaseTest;
import tonystarkindustries.pageobjects.CartPage;
import tonystarkindustries.pageobjects.CheckoutPage;
import tonystarkindustries.pageobjects.ConfirmationPage;
import tonystarkindustries.pageobjects.LandingPage;
import tonystarkindustries.pageobjects.ProductCatalogue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SubmitOrderTest extends BaseTest{
	String prdtName="ZARA COAT 3",cardMonth="12",cardYear="23", countryName="India";
	
	@Test(dataProvider = "getData", groups={"Purchase"})
																//First way of writing->//public void submitOrder(String email, String password, String prdtName) throws InterruptedException, IOException { 
	public void submitOrder(HashMap<String,String> input) throws InterruptedException, IOException {	
	// TODO Auto-generated method stub
		//String URL="https://rahulshettyacademy.com/client"; URL is already present in GLobal level in goTo fun

		//LandingPage landingPage=launchApplication(); LaunchApplication function is given the tag @BeforeMethod which runs first and also initializes the obj 
		
		/*LandingPage landingPage=new LandingPage(driver);//Obj of landingPage class has been created here and we are passing driver
		landingPage.goTo();                             //to access the site*/
		ProductCatalogue productCatalogue=landingPage.loginApplication(input.get("email"), input.get("password"));//PAssing values to login
		//ProductCatalogue productCatalogue=new ProductCatalogue(driver);

		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		
		CartPage cartPage=productCatalogue.goToCartPage();
		//CartPage cartPage = new CartPage(driver);
		cartPage.getCartList();
		Boolean flag=cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(flag); //Validations cannot go in Page Object files.
		                        //Page Object files should only have the code to perform actions.
		CheckoutPage checkOutPage=cartPage.goToCheckout();
		
		checkOutPage.selectCountry(countryName);
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("scrollBy(0,350)", " ");
		ConfirmationPage confirmationPage=checkOutPage.submitOrder(); //We caught our obj here as we made submitOrder actionmethod return this confirmationPage obj
		String confirmationMessage=confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
	}
	
	
	
	@Test(dependsOnMethods= {"submitOrder"}) //This depends on the above method to excute because only then a prdt will be visible in the orders section
	public void OrderHistoryTest() {
		ProductCatalogue productCatalogue=landingPage.loginApplication("blankname@gmail.com", "Blankname4$");//PAssing values to login
		OrdersPage ordersPage=productCatalogue.goToOrdersPage();
		Assert.assertTrue( ordersPage.verifyOrdersDisplay(prdtName)) ; //.verifyOrdersDisplay returns a boolean value
	}
	
	@DataProvider
	public Object[][] getData() throws IOException {
	
		List<HashMap<String,String>> data=getJasonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\tonystarkindustries\\data\\PurchaseOrder.json");
		return new Object[][]{{data.get(0)},{data.get(1)}};
	
		/*HashMap<String, String> map=new HashMap<String, String>();
		map.put("email", "blankname@gmail.com");
		map.put("password", "Blankname4$");
		map.put("product", "ZARA COAT 3");
		
		HashMap<String, String> map1=new HashMap<String, String>();
		map1.put("email", "blankname1@gmail.com");
		map1.put("password", "Blankname4$");
		map1.put("product", "ADIDAS ORIGINAL");*/		
		//First way of writing->//return new Object[][] {{"blankname@gmail.com","Blankname4$","ZARA COAT 3"},{"blankname1@gmail.com","Blankname4$","ADIDAS ORIGINAL"}};
	}
	
	
	
}
