package tonystarkindustries.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import tonystarkindustries.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener{
	ExtentTest test;
	ExtentReports extent=ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //Thread safe logic for creating unique thread each time parallel execution is executed.
					//IN this each test gets a unique thread and any processing occurring with that test will have unique ID and that
					//ID will be used further.
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestStart(result);
		test=extent.createTest(result.getMethod().getMethodName());//Dynamically taking the method name and setting it in the report
		extentTest.set(test); //Unique Thread ID will be set (ErrorValidationTest)->test
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSuccess(result);
		//test.log(Status.PASS, "Test Passed"); //This is for the report. IT DOESN't if write here because this is a pass condition
		extentTest.get().log(Status.PASS, "Test Passed"); //Replaced for the above statement for thread synchronization during parallel execution
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailure(result);
		System.out.println("This test failed: "+result.getName());
		//test.log(Status.FAIL, "The Test Failed"); //This is for the report
		//test.fail(result.getThrowable()); //This shows the error message in the report
		extentTest.get().fail(result.getThrowable());//Replacing the above statement with this for thread safe concept using ThreadLocal
		
		//Screenshot, Attach it to the report
		try {
			driver=(WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());//here we need class name as driver is associated with that class
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName()); //Replaced this with the below for the same reason as above
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
		System.out.println("This test Skipped: "+result.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
		extent.flush();
	}

}
