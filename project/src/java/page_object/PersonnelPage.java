package page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PersonnelPage {

    private WebDriver driver;
    private By email = By.xpath("//a[starts-with(@href, 'mailto')]");

    public PersonnelPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isPersonnelEmail(String text) {
        return driver.findElement(email).getText().equals(text);
    }
}
