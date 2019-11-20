package page_object;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import static java.lang.Thread.currentThread;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.Objects.requireNonNull;


public class DragDropPage {

    private By columnA = By.id("column-a");
    private By columnB = By.id("column-b");
    private By otherElement = By.id("page-footer");

    private WebDriver driver;

    public DragDropPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getColumnA() {
        return driver.findElement(columnA);
    }

    public WebElement getColumnB() {
        return driver.findElement(columnB);
    }

    public String getTextFromColumnA() {
        return driver.findElement(columnA).getText();
    }

    public String getTextFromColumnB() {
        return driver.findElement(columnB).getText();
    }

    public void dragAndDrop(WebElement source, WebElement target) {
        URL url = currentThread().getContextClassLoader().getResource("DragAndDrop.js");
        try {
            String script = IOUtils.toString(requireNonNull(url), defaultCharset());
            script += "simulateHTML5DragAndDrop(arguments[0], arguments[1])";
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript(script, source, target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTextFromColumns() {
        return driver.findElement(columnA).getText() + driver.findElement(columnB).getText();
    }

    public WebElement getOtherElement() {
        return driver.findElement(otherElement);
    }

    public boolean isTheFirstElementsHeaderText(String headerText) {
        return driver.findElement(columnA).findElement(By.tagName("header")).getText().equals(headerText);
    }

}
