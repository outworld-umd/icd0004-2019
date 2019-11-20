package ui_tests;

import page_object.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UITests {

    private WebDriver driver;
    private HomePage homePage;

    @BeforeClass
    public void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeMethod
    public void openHomePage() {
        homePage = new HomePage(driver);
    }

    @Test
    public void educationInfoHeaderMustBeQualityEducation() {
        StudentPage studentPage = new StudentPage(driver);
        EducationInfoPage educationInfoPage = new EducationInfoPage(driver);

        homePage.clickStudentLink();
        studentPage.clickEducationInfoLink();

        assertThat(educationInfoPage.isHeaderText("Kvaliteetne haridus"), is(true));
    }

    @Test
    public void germansEmailShouldBeCorrect() {
        PersonnelPage personnelPage = new PersonnelPage(driver);

        homePage.searchForPersonnelByName("German Mumma");

        assertThat(personnelPage.isPersonnelEmail("german.mumma@taltech.ee"), is(true));
    }

    @Test
    public void linkToOisShouldBeValid() {
        assertThat(homePage.isOisLink("https://ois2.ttu.ee/uusois/uus_ois2.tud_leht"), is(true));
    }

    @Test
    public void linkToMoodleShouldBeValid() {
        assertThat(homePage.isMoodleLink("https://moodle.taltech.ee/?lang=et"), is(true));
    }

    @Test
    public void linkToIntranetShouldBeValid() {
        assertThat(homePage.isIntranetLink("https://sise.ttu.ee/"), is(true));
    }

    @AfterClass
    public void closeDriver() {
        if (driver != null) {
            driver.close();
        }
    }
}

