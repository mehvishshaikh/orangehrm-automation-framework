package stepDefinitions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utilities.ExtentManager;
import utilities.MailUtil;
import utilities.TestBase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hooks extends TestBase {
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> scenarioThread = new ThreadLocal<ExtentTest>();

    @Before
    public void beforeScenario(Scenario scenario) {
        ExtentTest test = extent.createTest(scenario.getName());
        scenarioThread.set(test);
        ExtentManager.setTest(test);
    }

    @After
    public void afterScenario(Scenario scenario) throws IOException {
        ExtentTest test = scenarioThread.get();

        if (driver != null) {
            String screenshotPath = takeScreenshot(scenario.getName());
            test.addScreenCaptureFromPath(screenshotPath);
        }

        if (scenario.isFailed()) {
            test.log(Status.FAIL, "Scenario Failed");
        } else {
            test.log(Status.PASS, "Scenario Passed");
        }

        extent.flush();
    }

    private String takeScreenshot(String scenarioName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotName = scenarioName.replaceAll(" ", "_") + "_" + timestamp + ".png";
        String screenshotDir = "target/ExtentReports/Screenshots";
        String screenshotPath = screenshotDir + "/" + screenshotName;

        // Create folder if not exists
        new File(screenshotDir).mkdirs();

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(screenshotPath);
            FileUtils.copyFile(src, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return relative path so Extent can display it
        return "Screenshots/" + screenshotName;
    }

    @AfterAll
    public static void afterAll() {
        driver.quit(); // Quit only once after all scenarios
        MailUtil.sendExtentReport();
    }
}
