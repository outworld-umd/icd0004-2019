package page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class HomePage {

    private WebDriver driver;
    private By studentLink = By.xpath("//a[text()='Tudeng']");
    private By searchField = By.xpath("//input[@id='search-text']");
    private By personnelSearch = By.xpath("//label[2]");
    private By loginButton = By.cssSelector(".login");
    private By oisButton = By.xpath("//*[@id=\"dropdownHeader\"]/div/div[3]/ul/li/ul/li[1]/a");
    private By moodleButton = By.xpath("//*[@id=\"dropdownHeader\"]/div/div[3]/ul/li/ul/li[2]/a");
    private By intranetButton = By.xpath("//*[@id=\"dropdownHeader\"]/div/div[3]/ul/li/ul/li[3]/a");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://www.ttu.ee/");
    }

    public void clickStudentLink() {
        driver.findElement(studentLink).click();
    }

    public void searchForPersonnelByName(String name) {
        driver.findElement(searchField).click();
        driver.findElement(personnelSearch).click();
        driver.findElement(searchField).sendKeys(name + Keys.ENTER);
    }

    public void hoverOnLogin() {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(loginButton)).perform();
    }

    public boolean isOisLink(String link) {
        hoverOnLogin();
        return driver.findElement(oisButton).getAttribute("href").equals(link);
    }

    public boolean isMoodleLink(String link) {
        hoverOnLogin();
        return driver.findElement(moodleButton).getAttribute("href").equals(link);
    }

    public boolean isIntranetLink(String link) {
        hoverOnLogin();
        return driver.findElement(intranetButton).getAttribute("href").equals(link);
    }


}