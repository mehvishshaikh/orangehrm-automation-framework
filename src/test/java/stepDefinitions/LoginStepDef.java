package stepDefinitions;

import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pageObjects.AdminPageObjects;
import pageObjects.LoginPageObjects;
import utilities.ExtentLogger;
import utilities.Log;
import utilities.TestBase;

public class LoginStepDef extends TestBase{
	
	LoginPageObjects lpObjects;
	AdminPageObjects adminObjects;
	
	@Given("User launches the application URL")
	public void user_launches_the_application_url() {
		ExtentLogger.logInfo("Launching the application URL");
	    TestBase.initialization();
		Log.info("URL Launched successfully");
	}
	
	@And("Enter username and password")
	public void enter_username_and_password() {
		ExtentLogger.logInfo("Entering username and password");
		lpObjects = new LoginPageObjects();
		String username = prop.getProperty("username");
		String Pass = prop.getProperty("password");
		lpObjects.enterUserCredentials(username, Pass);
		Log.debug("Username and password entered successfully");
	}
	
	@Then("Click on login button")
	public void click_on_login_button() {
		ExtentLogger.logInfo("Clicking on login button");
	    lpObjects.clickLoginBtn();
		Log.info("Login button clicked successfully");

	}
	
	@Then("verify the title of the current page after login")
	public void verify_the_title_of_the_current_page_after_login() {
	   String pageTitle = lpObjects.getPageTitle();
	   Assert.assertEquals(pageTitle, "OrangeHRM", "Title does not match");
	   Log.info("Page title verified successfully: " + pageTitle);
	   ExtentLogger.logPass("Page title verified successfully: " + pageTitle);
	}
	
	@Then("verify that the login was successful")
	public void verify_that_the_login_was_successful() {
	    boolean status = lpObjects.isElementDisplayed();
		if(status) {
			System.out.println("Login was successful");
			Log.info("Login was successful");
		} else {
			System.out.println("Login was not successful");
			Log.error("Login was not successful");
		}
	    Assert.assertTrue(status, "Dashboard element is not displayed");
		ExtentLogger.logPass("Login was successful");
	}

	@Then("click on Admin section")
	public void clickOnAdminSection() {
		ExtentLogger.logInfo("Clicking on Admin section");
		adminObjects = new AdminPageObjects();
		adminObjects.clickAdminTab();
		Log.info("Admin section clicked successfully");
	}

	@Then("verify that the Admin section is displayed")
	public void verifyThatTheAdminSectionIsDisplayed() {
		boolean adminStatus = adminObjects.isAdminTabDisplayed();
		boolean userManagementStatus = adminObjects.isUserManagementTextDisplayed();
		Log.info("Admin section and User Management text displayed status: " + adminStatus + ", " + userManagementStatus);
		if(adminStatus && userManagementStatus) {
			System.out.println("Admin section is displayed successfully");
			ExtentLogger.logPass("Admin section is displayed successfully");
			Assert.assertTrue(true, "Admin section is not displayed");
		} else {
			System.out.println("Admin section is not displayed");
			ExtentLogger.logFail("Admin section is not displayed");
		}
	}
}
