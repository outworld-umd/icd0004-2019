package page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StudentPage {

    private WebDriver driver;
    private By educationInfoLink = By.linkText("Õppeinfo");

    public StudentPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickEducationInfoLink() {
        driver.findElement(educationInfoLink).click();
    }
}
