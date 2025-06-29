package utilities;

import com.aventstack.extentreports.Status;

public class ExtentLogger {

    public static void logInfo(String message) {
        ExtentManager.getTest().log(Status.INFO, message);
    }

    public static void logPass(String message) {
        ExtentManager.getTest().log(Status.PASS, message);
    }

    public static void logFail(String message) {
        ExtentManager.getTest().log(Status.FAIL, message);
    }

    public static void logWarning(String message) {
        ExtentManager.getTest().log(Status.WARNING, message);
    }
}
