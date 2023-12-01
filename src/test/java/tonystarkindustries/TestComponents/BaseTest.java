package tonystarkindustries.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import tonystarkindustries.pageobjects.LandingPage;

public class BaseTest {
//This class is made to reuse all the initilizations for tests. Ex: WebDrivers, etc
	public WebDriver driver;// Declared this globally to access this driver easily
	// Initially this driver is null but it gets life after entering into the of
	// conditions
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {
		// properties class is used to use data in GlobalData.properties
		Properties prop = new Properties();// Imported properties class and created an object
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\main\\java\\tonystarkindustries\\resources\\GlobalData.properties");
		prop.load(fis); // See below comments to understand
		
		String browserName=System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");//We were initially using the below statement but to run the test case in a specified browser through the cmd prompt we changed it to this one using ternary operator
		//String browserName = prop.getProperty("browser"); // Accessing browser attribute value from
															// GlobalData.properties
		if (browserName.contains("chrome")) {
			ChromeOptions options =new ChromeOptions();
			WebDriverManager.chromedriver().setup();		
			if(browserName.contains("headless")) {
			options.addArguments("headless");	
			}	
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension (1440,900));//making sure that browser opens in maximize mode in headless mode (this is on top off maximize and basically means to tun on fullscreen)
		} else if (browserName.equalsIgnoreCase("firefox")) {
			// Firefox
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(); 
		} else if (browserName.equalsIgnoreCase("edge")) {
			// Edge
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();// or System.setProperty("webdriver.edge.driver","<DriverPath>");
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		return driver;
		/*
		 * //prop.load(<requires an Input stream>) for which we need FileInputStream
		 * with an obj which is created as 'fis' and file path is sent into it. //The
		 * obj 'fis' now converts the file path into a stream and send it in the load
		 * method of the properties class. //THrough this, we can now extract any of the
		 * globalproperties from the GlobalData.properties file
		 */
	}
	
	public String getScreenshot(String testCase, WebDriver driver) throws IOException {
		TakesScreenshot ts=(TakesScreenshot) driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		File filePath = new File(System.getProperty("user.dir")+"\\reports\\"+testCase+".png");
		FileUtils.copyFile(src, filePath);
		return System.getProperty("user.dir")+"\\reports\\"+testCase+".png";
	
	}
	
	@BeforeMethod(alwaysRun = true) // Getting this executed first everyTime we run this code, while initializing
									// the Landing Page obj here
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		// LandingPage landingPage=new LandingPage(driver);//Obj of landingPage class
		// has been created here and we are passing driver
		landingPage = new LandingPage(driver);// Class name is defined as public for it to be accessible by other
												// classes
		landingPage.goTo(); // to access the site
		return landingPage; //
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
	
	/*
	 * While running groups in TestNG it is suggested to create a separate file for
	 * different groups depending on the need. While grouping
	 * TestNG skips @BeforeMethod and @AfterMethod which are main methods needed to
	 * initialize a driver and close the driver. Here, one solution is to grp them
	 * again, but that idea will limit those reusable methods to that grp only which
	 * is not feasible and not acceptable. Therefore, TestNG also has one other
	 * grouping name called as (alwaysRun = true). This will ensure
	 * These @BeforeMethod and @AfterMethod will not be skipped while running any
	 * groups!
	 */
	///using the below method to send data by converting into hashmap using ObjectMapper
	public List<HashMap<String, String>> getJasonDataToMap(String filePath) throws IOException {
		//String jsonContent=FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\tonystarkindustries\\data\\PurchaseOrder.json"),StandardCharsets.UTF_8);
																					//THis file path is hardCoded
		//To make it generic we need to send the file path from the Test
		String jsonContent=FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		//String to HashMAp -> using DataBind for ObjectMaper class
		ObjectMapper mapper =new ObjectMapper();
		List<HashMap<String,String>> data=mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){		
		});
		return data;
		//{map,map}
	}
}
