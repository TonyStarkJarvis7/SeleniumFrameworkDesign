package tonystarkindustries.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import tonystarkindustries.pageobjects.LandingPage;

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

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String prdtName="ZARA COAT 3",cardMonth="12",cardYear="23";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		JavascriptExecutor js=(JavascriptExecutor) driver;
		
		driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("tonystark@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Cognizant1*");
		driver.findElement(By.id("login")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class*='mb-3']")));
		List<WebElement> products=driver.findElements(By.cssSelector("[class*='mb-3']"));
		
		WebElement prod=products.stream().filter(i->
		i.findElement(By.cssSelector("b")).getText().equals(prdtName)).findFirst().orElse(null);
		/*for(int i=0;i<products.size();i++) {
			String name=products.get(i).getText();
			System.out.println(name);
		}
		System.out.println("----------------------");*/
		products.stream().forEach(i->System.out.println(i.getText()+"-----------------"));
		
		prod.findElement(By.cssSelector("[class='btn w-10 rounded']")).click();
		String productAddedToCart=driver.findElement(By.cssSelector("[aria-label='Product Added To Cart']")).getText();
		System.out.println(productAddedToCart);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label='Product Added To Cart']")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		//The class name of the loading element was given by the instructor directly and that is
				//ng-animating
		Assert.assertEquals(productAddedToCart,"Product Added To Cart");
		
		driver.findElement(By.cssSelector("[routerlink='/dashboard/cart']")).click();
		List<WebElement> cartItems= driver.findElements(By.cssSelector("[class='cartSection']"));
		boolean flag=cartItems.stream().anyMatch(i->i.findElement(By.xpath("//div[@class='cartSection']//h3")).getText().equalsIgnoreCase(prdtName));
		Assert.assertTrue(flag);
		driver.findElement(By.xpath("//button[contains(text(),'Checkout')]")).click();
		
		WebElement expiryMonth=driver.findElement(By.xpath("//div[@class='field small']//select[@class='input ddl'][1]"));
		Select em=new Select(expiryMonth);
		em.selectByVisibleText(cardMonth);
		
		/*WebElement expiryYear=driver.findElement(By.xpath("//div[@class='field small']//select[@class='input ddl'][2]"));
		Select ey=new Select(expiryYear);  
		ey.selectByValue(cardYear);*/
		
		WebElement countryDropdown=driver.findElement(By.cssSelector("[placeholder='Select Country']"));
		countryDropdown.sendKeys("india");
		
		List<WebElement> dd=driver.findElements(By.cssSelector("[class*='ta-item']"));
		dd.stream().filter(i->i.getText().equalsIgnoreCase("India")).forEach(i->i.click());
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[class*='ta-item']")));
		js.executeScript("window.scrollBy(0,500)", " ");
		driver.findElement(By.cssSelector("[class*='action__submit ']")).click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("[class='hero-primary']")).getText(), "THANKYOU FOR THE ORDER.");
	}

}
