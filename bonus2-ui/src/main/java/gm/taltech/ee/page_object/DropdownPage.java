package gm.taltech.ee.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class DropdownPage {
    private WebDriver driver;
    private By dropdownField = By.id("dropdown");

    public DropdownPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectOptionOne() {
        driver.findElement(dropdownField).click();
        Select dropdown = new Select(driver.findElement(dropdownField));
        dropdown.selectByVisibleText("Option 2");
    }

    public boolean isOptionOneSelected() {
        Select dropdown = new Select(driver.findElement(dropdownField));
        return dropdown.getFirstSelectedOption().getText().equals("Option 2");
    }
}
