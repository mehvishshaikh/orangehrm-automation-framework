package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

        private static ExtentReports extent;
        private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<ExtentTest>();

        public static ExtentReports getInstance() {
            if (extent == null) {
                // Generate timestamp
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String reportPath = "target/ExtentReports/ExtentReport_" + timestamp + ".html";

                // Create ExtentSparkReporter with dynamic file name
                ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
                spark.config().setReportName("Automation Test Report");
                spark.config().setDocumentTitle("Test Execution Results");

                extent = new ExtentReports();
                extent.attachReporter(spark);
                extent.setSystemInfo("Environment", "QA");
                extent.setSystemInfo("Tester", "Mehvish");
            }
            return extent;
        }

        public static void setTest(ExtentTest test) {
            testThread.set(test);
        }

        public static ExtentTest getTest() {
            return testThread.get();
        }
}
