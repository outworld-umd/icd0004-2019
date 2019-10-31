package gm.taltech.ee;

import gm.taltech.ee.page_object.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TheInternetAppTests {

    private WebDriver driver;

    @BeforeClass
    public void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeMethod
    public void openDriver() {
        driver.get("https://the-internet.herokuapp.com/");
    }

    // Exercise 1. Form Authentication
    @Test
    public void shouldDisplayUsernameInvalidMessage() {
        HomePage homePage = new HomePage(driver);
        FormAuthenticationPage formAuthPage = new FormAuthenticationPage(driver);

        homePage.clickFormAuthenticationLink();
        formAuthPage.enterUsername("hello");
        formAuthPage.clickSubmit();

        assertThat(formAuthPage.getErrorNotificationText(), is("Your username is invalid!"));
    }

    @Test
    public void shouldDisplayPasswordInvalidMessage() {
        HomePage homePage = new HomePage(driver);
        FormAuthenticationPage formAuthPage = new FormAuthenticationPage(driver);

        homePage.clickFormAuthenticationLink();
        formAuthPage.enterUsername("tomsmith");
        formAuthPage.enterPassword("tom123456");
        formAuthPage.clickSubmit();

        assertThat(formAuthPage.getErrorNotificationText(), is("Your password is invalid!"));
    }

    @Test
    public void shouldDisplaySuccessfullyLoggedInMessage() {
        HomePage homePage = new HomePage(driver);
        FormAuthenticationPage formAuthPage = new FormAuthenticationPage(driver);

        homePage.clickFormAuthenticationLink();
        formAuthPage.enterUsername("tomsmith");
        formAuthPage.enterPassword("SuperSecretPassword!");
        formAuthPage.clickSubmit();

        assertThat(formAuthPage.getSuccessNotificationText(), is("You logged into a secure area!"));
    }

    @Test
    public void shouldDisplaySuccessfullyLoggedOutMessage() {
        HomePage homePage = new HomePage(driver);
        FormAuthenticationPage formAuthPage = new FormAuthenticationPage(driver);

        homePage.clickFormAuthenticationLink();
        formAuthPage.enterUsername("tomsmith");
        formAuthPage.enterPassword("SuperSecretPassword!");
        formAuthPage.clickSubmit();
        formAuthPage.clickLogout();

        assertThat(formAuthPage.getSuccessNotificationText(), is("You logged out of the secure area!"));
    }

    // Exercise 2. Drag and Drop
    @Test
    public void givenOrderABWhenAIsDraggedOnBOrderChangesToBA() {
        HomePage homePage = new HomePage(driver);
        DragDropPage dragDropPage = new DragDropPage(driver);

        homePage.clickDragDropLink();
        dragDropPage.dragAndDrop(dragDropPage.getColumnA(), dragDropPage.getColumnB());

        assertThat(dragDropPage.getTextFromColumns(), is("BA"));
    }

    @Test
    public void givenOrderABWhenAIsDraggedNotOnBOrderStaysSame() {
        HomePage homePage = new HomePage(driver);
        DragDropPage dragDropPage = new DragDropPage(driver);

        homePage.clickDragDropLink();
        dragDropPage.dragAndDrop(dragDropPage.getColumnA(), dragDropPage.getOtherElement());

        assertThat(dragDropPage.getTextFromColumns(), is("AB"));
    }

    // Exercise 3. Multiple Windows
    @Test
    public void shouldSeeTextNewWindowAfterClickingLinkClickHere () {
        HomePage homePage = new HomePage(driver);
        MultipleWindowsPage multipleWindowsPage = new MultipleWindowsPage(driver);

        homePage.clickMultipleWindowsLink();
        multipleWindowsPage.clickNewWindowLink();

        assertThat(multipleWindowsPage.isNewWindowVisible(), is(true));
    }

    @AfterClass
    public void closeDriver() {
        if (driver != null) {
            driver.close();
        }
    }
}
