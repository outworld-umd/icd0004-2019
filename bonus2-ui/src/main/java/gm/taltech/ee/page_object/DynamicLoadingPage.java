package gm.taltech.ee.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class DynamicLoadingPage {

    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private By exampleOne = By.linkText("Example 1: Element on page that is hidden");
    private By startButton = By.cssSelector("#start > button");
    private By helloWorldTitle = By.cssSelector("#finish > h4");

    public DynamicLoadingPage(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(this.driver, 5);
    }

    public void clickExampleOne() {
        driver.findElement(exampleOne).click();
    }

    public void clickStart() {
        driver.findElement(startButton).click();
    }

    public boolean isHelloWorldVisible() {
        WebElement helloWorldElement = webDriverWait.until(visibilityOfElementLocated(helloWorldTitle));
        return helloWorldElement.getText().equals("Hello World!");
    }
}
