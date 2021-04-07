import jdk.jfr.Description;
import main.Test.WaitForPageLoad;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.yandex.qatools.allure.annotations.Title;

public class SeleniumTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void openPage() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("window-size=1200x600");
        System.setProperty("webdriver.chrome.driver", "D:\\JavaKotlinProjects\\ChromeDriver\\chromedriver89.exe");
        driver = new ChromeDriver(options);
    }

    @Title("Creating new user")
    @Description("trying to create new user")
    @org.testng.annotations.Test
    public void testLoginPage() throws InterruptedException {
        driver.get("http://localhost:8080");

        WebElement el = driver.findElement(By.id("name"));
        el.sendKeys("admin");
        el = driver.findElement(By.id("password"));
        el.sendKeys("admin");
        el = driver.findElement(By.id("login"));
        el.click();
        WaitForPageLoad.waitForPageLoad(driver);
        synchronized (driver) {
            driver.wait(1000);
        }
        el = driver.findElement(By.id("new_user"));
        el.click();
        el = driver.findElement(By.id("new_name"));
        el.sendKeys("SeleniumUser");
        el = driver.findElement(By.id("new_email"));
        el.sendKeys("SeleniumUser@selenium.com");
        el = driver.findElement(By.id("new_password"));
        el.sendKeys("12345");
        el = driver.findElement(By.id("add_user"));
        el.click();
    }

    @AfterMethod
    public void teardown(){
        driver.close();
    }
}


