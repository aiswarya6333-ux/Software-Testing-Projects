package basePgm;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
	
	protected static WebDriver driver;
    protected static WebDriverWait wait;

    protected static final String URL =
            "https://demo.prestashop.com/#/en/front";

    @BeforeSuite
    public void startBrowser() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );

        driver.get(URL);

        // Wait for PrestaShop iframe
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.id("framelive")
                )
        );

        switchToPrestaShopFrame();

        System.out.println("Browser started successfully.");
    }

    public void switchToPrestaShopFrame() {

        driver.switchTo().defaultContent();

        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                        By.id("framelive")
                )
        );
    }

    @AfterSuite
    public void closeBrowser() {

        if (driver != null) {

            driver.quit();

            System.out.println(
                    "Browser closed successfully."
            );
        }
    }

}
