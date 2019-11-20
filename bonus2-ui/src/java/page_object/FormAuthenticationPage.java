package page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;

public class FormAuthenticationPage {

    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By submitButton = By.xpath("//button[@type='submit']");
    private By logoutButton = By.xpath("//a[@href='/logout']");
    private By successNotification = By.cssSelector(".flash.success");
    private By errorNotification = By.cssSelector(".flash.error");

    private WebDriver driver;

    public FormAuthenticationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickSubmit() {
        driver.findElement(submitButton).click();
    }

    public void clickLogout() {
        driver.findElement(logoutButton).click();
    }

    public boolean isSuccessNotificationDisplayed() {
        try {
            driver.findElement(successNotification);
            return true;
        } catch (ElementNotVisibleException e) {
            return false;
        }
    }

    public boolean isErrorNotificationDisplayed() {
        try {
            driver.findElement(errorNotification);
            return true;
        } catch (ElementNotVisibleException e) {
            return false;
        }
    }

    public String getSuccessNotificationText() {
        return driver.findElement(successNotification).getText().replace("\n×", "");
    }

    public String getErrorNotificationText() {
        return driver.findElement(errorNotification).getText().replace("\n×", "");
    }

    public boolean isSuccessNotificationText(String text) {
        return driver.findElement(successNotification).getText().replace("\n×", "").equals(text);
    }

    public boolean isErrorNotificationText(String text) {
        return driver.findElement(errorNotification).getText().replace("\n×", "").equals(text);
    }

}
