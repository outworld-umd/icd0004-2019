package page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class MultipleWindowsPage {

    private WebDriver driver;

    private By newWindowLink = By.xpath("//a[@href='/windows/new']");
    private By newWindowTitle = By.cssSelector(".example > h3");

    public MultipleWindowsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickNewWindowLink() {
        driver.findElement(newWindowLink).click();
    }

    public boolean isNewWindowVisible() {
        String parentWindow = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) driver.switchTo().window(winHandle);
        String newWindowMsg = driver.findElement(newWindowTitle).getText();
        driver.close();
        driver.switchTo().window(parentWindow);
        return newWindowMsg.equals("New Window");
    }


}
