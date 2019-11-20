package page_object;

import org.openqa.selenium.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    // Actions.dragAndDrop seems to be broken in html5, so this dragAndDrop is implemented through js
    public void dragAndDrop(WebElement src, WebElement target) {
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/scripts/dnd.js"));
            while ((line = br.readLine()) != null) builder.append(line);
            String javaScript = builder.toString();
            javaScript += "$('#" + src.getAttribute("id") +
                    "').simulateDragDrop({ dropTarget: '#" + target.getAttribute("id") + "'});";
            ((JavascriptExecutor) driver).executeScript(javaScript);
        } catch (IOException ignored) {}
    }

    public String getTextFromColumns() {
        return driver.findElement(columnA).getText() + driver.findElement(columnB).getText();
    }

    public WebElement getOtherElement() {
        return driver.findElement(otherElement);
    }
}
