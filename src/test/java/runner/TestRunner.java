package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/resources/features",           // Path to your feature files
	    glue = "stepDefinitions",                           // Package containing step definitions
	    plugin = {
	        "pretty",                                       // Console output
				"html:target/cucumber-reports.html",            // Basic HTML report
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // Extent Report
	    },
	    monochrome = true,                                   // Removes unreadable console characters
	    dryRun = false
	)

public class TestRunner extends AbstractTestNGCucumberTests{

}
