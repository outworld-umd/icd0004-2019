package gm.taltech.ee.page_object;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class HoversPage {

    private WebDriver driver;
    private By profilePicture = By.className("figure");
    private By viewProfileLink = By.linkText("View profile");

    public HoversPage(WebDriver driver) {
        this.driver = driver;
    }

    public void hoverOverFirstProfile() {
        WebElement firstProfilePicture = driver.findElements(profilePicture).get(0);
        Actions actions = new Actions(driver);
        actions.moveToElement(firstProfilePicture).perform();
    }

    public boolean isViewProfileLinkVisible() {
        try {
            driver.findElement(viewProfileLink);
            return true;
        } catch (NoSuchElementException e) {
            // log exception
            return false;
        }
    }
}
