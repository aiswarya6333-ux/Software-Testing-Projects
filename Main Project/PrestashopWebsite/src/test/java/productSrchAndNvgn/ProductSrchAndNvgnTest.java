package productSrchAndNvgn;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import basePgm.BaseTest;

public class ProductSrchAndNvgnTest extends BaseTest{
	
	// Search box
    By searchBox = By.xpath("//*[@id=\"ps_searchbar\"]/form/input[2]");

    // Search button
    By searchButton = By.xpath("//*[@id=\"ps_searchbar\"]/form/i");
    
    // frames product search results
    By frameSearchResults = By.xpath("//h1[text()='Search results for \\\"frames\\\"']");
    
    //invalid product search results
    By searchResults = By.xpath("//*[@id=\"center-column\"]");

    // Categories
    By categories = By.xpath("//*[@id=\"top-menu\"]");        
    

    @Test(priority = 1)
    public void validProductSearch() {

        WebElement search = wait.until(
                ExpectedConditions.elementToBeClickable(searchBox));

//        search.clear();
        search.sendKeys("Hummingbird printed sweater");

        search.sendKeys(Keys.ENTER);
        
        String heading = driver.findElement(By.xpath("//a[text()='Hummingbird printed sweater']")).getText();
        
        Assert.assertTrue(
                heading.contains("Hummingbird printed sweater"));

        System.out.println("Valid product search - Passed");
    }


    // =========================
    // PS_TC50
    // Product search with partial product name
    // =========================

    @Test(priority = 2)
    public void partialProductSearch() {

        WebElement search = wait.until(
                ExpectedConditions.elementToBeClickable(searchBox)
        );

        search.clear();
        search.sendKeys("Sweater");

        search.sendKeys(Keys.ENTER);
        
        String heading = driver.findElement(By.xpath("//a[text()='Hummingbird printed sweater']")).getText();
        
        Assert.assertTrue(
                heading.contains("Hummingbird printed sweater"));

        System.out.println("Partial product search - Passed");

    }


    // =========================
    // PS_TC51
    // Product Search with invalid keyword
    // =========================

    @Test(priority = 3)
    public void invalidKeyword() {

        WebElement search = wait.until(
                ExpectedConditions.elementToBeClickable(searchBox)
        );

        search.clear();
        search.sendKeys("12345");
        search.sendKeys(Keys.ENTER);

        String pageText = driver.findElement(searchResults).getText();       

        Assert.assertTrue(
        		pageText.contains("Nothing to search for"),
                "Expected validation/no-results message was not displayed"
        );
    }


    // =========================
    // PS_TC52
    // Product search by clicking search icon
    // =========================

    @Test(priority = 4)
    public void productSearchByClickingSearchIcon() {

        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        search.clear();
        search.sendKeys("frames");

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        button.click();

        WebElement searchResults = wait.until(
                ExpectedConditions.visibilityOfElementLocated(frameSearchResults));

        Assert.assertTrue(searchResults.isDisplayed(),
        		"Search results page was not displayed");
    }


    // =========================
    // PS_TC53
    // Product search with blank input
    // =========================

    @Test(priority = 5)
    public void productSearchWithBlankInput() {

        WebElement search = wait.until(
                ExpectedConditions.elementToBeClickable(searchBox)
        );

        search.clear();

        search.sendKeys(Keys.ENTER);

        String pageText = driver.findElement(searchResults).getText();         

        Assert.assertTrue(
                pageText.contains("Nothing to search for")
                        || pageText.contains("Search again"),
                "Blank search validation message was not displayed"
        );
    }
    
    
    // =========================
    // PS_TC58
    // Case sensitivity
    // =========================

    @Test(priority = 6)
    public void verifySearchCaseInsensitive() {

        // Uppercase search
        WebElement search = wait.until(
                ExpectedConditions.elementToBeClickable(searchBox)
        );

        search.clear();
        search.sendKeys("SHIRT");
        search.sendKeys(Keys.ENTER);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Search results for \"SHIRT\"']"))
        );

        int uppercaseResults =
                driver.findElements(By.xpath("//*[@id=\"wrapper\"]")).size();

        // Lowercase search
        search = wait.until(
                ExpectedConditions.elementToBeClickable(searchBox)
        );

        search.clear();
        search.sendKeys("shirt");
        search.sendKeys(Keys.ENTER);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Search results for \"shirt\"']"))
        );

        int lowercaseResults =
                driver.findElements(By.xpath("//*[@id=\"wrapper\"]")).size();            

        Assert.assertEquals(
                uppercaseResults,
                lowercaseResults,
                "Uppercase and lowercase searches returned different results"
        );
    }

}
