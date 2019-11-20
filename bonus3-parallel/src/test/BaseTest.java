import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.util.LinkedList;

public class BaseTest {

    private static final String BROWSER = System.getProperty("browser") != null ? System.getProperty("browser") : "";
    private static LinkedList<WebDriver> availableDrivers = new LinkedList<>();

    ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static RemoteWebDriver getBrowser() {
        switch (BROWSER) {
            case "firefox": return new FirefoxDriver();
            case "edge": return new EdgeDriver();
            case "opera": return new OperaDriver();
            default: return new ChromeDriver();
        }
    }

    @BeforeTest
    public void setUpDriver() {
        switch (BROWSER) {
            case "firefox": WebDriverManager.firefoxdriver().setup(); break;
            case "edge": WebDriverManager.edgedriver().setup(); break;
            case "opera": WebDriverManager.operadriver().setup(); break;
            default: WebDriverManager.chromedriver().setup(); break;
        }
    }

    @BeforeMethod
    public void openDriver() {
        driver.set(availableDrivers.isEmpty() ? getBrowser() : availableDrivers.pop());
    }

    @AfterMethod
    public void saveAvailableDriver() {
        availableDrivers.add(driver.get());
    }

    @AfterTest
    public void closeDrivers() {
        while (!availableDrivers.isEmpty()) availableDrivers.pop().close();
    }
}
