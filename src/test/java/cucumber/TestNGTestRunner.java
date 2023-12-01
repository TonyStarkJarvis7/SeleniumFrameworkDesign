package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features="src/test/java/cucumber",glue="tonystarkindustries.stepDefinitions",
monochrome=true,tags= "@Regression", plugin= {"html:target/cucumber.html"})
public class TestNGTestRunner extends AbstractTestNGCucumberTests{

	//we need a testNG-Cucumber runner file to execute cucumber feature files and this is the Runner file
	/*
	 * AbstractTestNGCucumberTests is a Abstract TestNG Cucumber Test which
	 * Runs each cucumber scenario found in the features as separated test created by TestNG to help Cucumber
	 * read TestNG related data and Annotations. Therefore, the Runner file extends to it
	 * 
	 * @CucumberOptions makes this a runner file in which the feature=requires the path of the cucumber package
	 * glue=shows the path of the setDefinition files
	 * monochrome=true-> makes the cucumber output in readable format
	 * plugin= is the format(html) and path where the cucumber report would be stored 
	 * When Tags are used, only those scenarios run which have the tags.
	 * 
	 * We use this runner file because Cucumber has its own complete world but it cannot run the files on its own
	 * It needs either TestNG or JUnit to run the code and this decision depends upon The type of assertions we 
	 * used including the structure of the project, etc.
	 */
}
