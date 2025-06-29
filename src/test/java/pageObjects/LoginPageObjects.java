package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.TestBase;

public class LoginPageObjects extends TestBase{
	
	//Page Factory Model
	@FindBy(name = "username")
	WebElement username;
	
	@FindBy(name = "password")
	WebElement password;
	
	@FindBy(xpath = "//button[@type = 'submit']")
	WebElement loginBtn;
	
	@FindBy(xpath = "//h6[text() = 'Dashboard']")
	WebElement afterLoginText;
	
	public LoginPageObjects() {
		PageFactory.initElements(driver, this);
	}
	
	public void enterUserCredentials(String un, String pass) {
		username.sendKeys(un);
		password.sendKeys(pass);
	}
	
	public void clickLoginBtn() {
		loginBtn.click();
	}
	
	public boolean isElementDisplayed() {
		return afterLoginText.isDisplayed();
	}
	
	public String getPageTitle() {
		return driver.getTitle();
	}

}
