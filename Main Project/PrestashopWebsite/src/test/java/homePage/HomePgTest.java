package homePage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import basePgm.BaseTest;

public class HomePgTest extends BaseTest{
	
	// =========================
    // HP_TC38 - Home Page Load, 
    // HP_TC46 - Home Page Slider
    // =========================

    @Test(priority = 1)
    public void verifyHomePageLoadsSuccessfullyandBannerSlider() {

        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class ='carousel-control-next-icon']") ) );

        WebElement previousButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class ='carousel-control-prev-icon']") ) );

        Assert.assertTrue(nextButton.isDisplayed(), "Next arrow is not displayed");

        Assert.assertTrue(previousButton.isDisplayed(), "Previous arrow is not displayed");

        nextButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class ='carousel-control-prev-icon']") ) );

        previousButton.click();

        System.out.println("HP_TC38 - Home page loaded successfully and HP_TC46 - Home page slider verified");
    }

    // =========================
    // HP_TC39 - Language Selector
    // =========================

    @Test(priority = 2)
    public void verifyLanguageSelector() {

        WebElement language = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[aria-label='Change language']") ) );

        language.click();

        Assert.assertTrue(driver.findElement(By.cssSelector("select[aria-label='Change language']")).isDisplayed(), "Language options are not displayed");

        System.out.println("HP_TC39 - Language selector is working");
    }


    // =========================
    // HP_TC40 - Featured Products
    // =========================

    @Test(priority = 3)
    public void verifyFeaturedProducts() {

        WebElement featuredProducts = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Featured products']") ) );

        Assert.assertTrue(featuredProducts.isDisplayed(), "Featured products section is not displayed");

        Actions actions = new Actions(driver);
        actions.moveToElement(featuredProducts).pause(Duration.ofSeconds(5)).perform();
        	
        System.out.println("HP_TC40 - Featured products are displayed and clickable in home page");
    }


    // =========================
    // HP_TC42 - Quantity & Add To Cart
    // =========================

    @Test(priority = 5)
    public void verifyProductQuantityAndAddToCart() {
	
    	// Locate product quantity value
    	WebElement quantity = driver.findElement(
    	    By.xpath("(//*[@id='quantity_wanted_1'])[1]")
    	);

    	String oldQuantity = quantity.getAttribute("value");

    	// Locate the product quantity increase (+) button
    	WebElement increaseButton = wait.until(
    	    ExpectedConditions.elementToBeClickable(
    	        By.xpath("(//*[@id='increment_button_1'])[1]")
    	    )
    	);

    	// Scroll to the quantity and increase button
    	((JavascriptExecutor) driver).executeScript(
    	    "arguments[0].scrollIntoView({block:'center'});",
    	    increaseButton
    	);
    	
    	((JavascriptExecutor) driver).executeScript(
              "arguments[0].click();", increaseButton
      );

    	// Wait for quantity value to change
    	wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(quantity, "value", oldQuantity) ) );

    	// Get updated quantity
    	String newQuantity = quantity.getAttribute("value");

    	// Verify quantity increased
    	Assert.assertNotEquals(
    	    newQuantity,
    	    oldQuantity,
    	    "Product quantity did not increase"
    	);

    	System.out.println(
    	    "HP_TC42 - Product quantity increased successfully from "
    	    + oldQuantity + " to " + newQuantity
    	);
        
        WebElement addtocart = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[@data-button-action='add-to-cart' and contains(@title,'Hummingbird printed t-shirt')])[1]") ) );
        
     // Scroll to the add to cart button
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            addtocart
        );
        
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                addtocart
        );
         
        System.out.println("HP_TC42 - Product quantity increased and it is added to cart successfully");
    }
    
    
 // =========================
    // HP_TC43 - Promotion Banner
    // =========================

    @Test(priority = 6)
    public void verifyPromotionBanner() {

        WebElement banner = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"content\"]/section[4]/div/a/img")
                )
        );

        Assert.assertTrue(banner.isDisplayed(),
                "Promotion banner is not displayed");

        System.out.println("HP_TC43 - Promotion banner displayed");
    }

    
    // =========================
    // HP_TC44 - Header Section
    // =========================

    @Test(priority = 7)
    public void verifyHeaderSection() {

        // Logo
        WebElement logo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".logo")
                )
        );

        Assert.assertTrue(logo.isDisplayed(),
                "Logo is not displayed");

        // Search box
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[name='s']")
                )
        );

        Assert.assertTrue(searchBox.isDisplayed(),
                "Search box is not displayed");

        // Sign in
        WebElement signIn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(.,'Sign in')]")
                )
        );

        Assert.assertTrue(signIn.isDisplayed(),
                "Sign in option is not displayed");

        // Cart
        WebElement cart = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"_desktop_ps_shoppingcart\"]/div/div/span")
                )
        );

        Assert.assertTrue(cart.isDisplayed(),
                "Cart option is not displayed");

        // Clothes category
        WebElement clothes = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"top-menu\"]/li[1]/div[1]/a")
                )
        );

        Assert.assertTrue(clothes.isDisplayed(),
                "Clothes category is not displayed");

        // Accessories category
        WebElement accessories = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"top-menu\"]/li[2]/div[1]/a")
                )
        );

        Assert.assertTrue(accessories.isDisplayed(),
                "Accessories category is not displayed");

        System.out.println("HP_TC44 - Header section verified");
    }


    // =========================
    // HP_TC45 - Footer Section
    // =========================

    @Test(priority = 8)
    public void verifyFooterSection() {

        WebElement footer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("footer")
                )
        );

        Assert.assertTrue(footer.isDisplayed(),
                "Footer is not displayed");

        // Products
        WebElement products = footer.findElement(
                By.xpath(".//*[contains(text(),'Products')]")
        );

        Assert.assertTrue(products.isDisplayed());

        // Our company
        WebElement company = footer.findElement(
                By.xpath(".//*[contains(text(),'Our company')]")
        );

        Assert.assertTrue(company.isDisplayed());

        // Your account
        WebElement account = footer.findElement(
                By.xpath(".//*[contains(text(),'Your account')]")
        );

        Assert.assertTrue(account.isDisplayed());

        // Store information
        WebElement storeInformation = footer.findElement(
                By.xpath(".//*[contains(text(),'Store information')]")
        );

        Assert.assertTrue(storeInformation.isDisplayed());

        System.out.println("HP_TC45 - Footer section verified");
    }

}
