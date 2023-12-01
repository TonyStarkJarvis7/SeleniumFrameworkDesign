package tonystarkindustries.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{//This retries after execution of Listeners also and 
	
	int count=0;
	int maxTry=1;

	@Override
	public boolean retry(ITestResult result) { //Write any logic and as long as the logic returns true the test will keep re-executing
		// TODO Auto-generated method stub
		if(count<maxTry) {
			count++;
			return true;   //This would re-execute the failed functions as long as this functions returns true
		}
		return false;
	}

}
