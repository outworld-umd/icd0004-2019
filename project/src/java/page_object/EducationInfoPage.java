package page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EducationInfoPage {

    private WebDriver driver;
    private By mainTitle = By.tagName("h2");

    public EducationInfoPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isHeaderText(String text) {
        return driver.findElement(mainTitle).getText().equals(text);
    }
}
