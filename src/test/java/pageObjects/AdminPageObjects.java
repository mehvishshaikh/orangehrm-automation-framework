package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.TestBase;

public class AdminPageObjects extends TestBase {

    @FindBy (xpath = "//span[text() = 'Admin']")
    WebElement adminTab;

    @FindBy (xpath = "//span[text() = 'Admin']")
    WebElement adminTabText;

    @FindBy (xpath = "//h6[text() = 'User Management']")
    WebElement userManagementText;

    public AdminPageObjects() {
        PageFactory.initElements(driver, this);
    }

    public void clickAdminTab() {
        adminTab.click();
    }

    public boolean isAdminTabDisplayed() {
        return adminTabText.isDisplayed();
    }

    public boolean isUserManagementTextDisplayed() {
        return userManagementText.isDisplayed();
    }
}
