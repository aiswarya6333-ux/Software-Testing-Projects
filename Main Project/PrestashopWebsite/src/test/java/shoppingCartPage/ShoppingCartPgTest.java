package shoppingCartPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import basePgm.BaseTest;

public class ShoppingCartPgTest extends BaseTest{
	
	// Product on Home Page
    By addproductToCart = By.xpath("(//button[@data-button-action='add-to-cart' and contains(@title,'Hummingbird printed t-shirt')])[1]");

    // Cart icon in header
    By cartIcon = By.id("desktop_ps_shoppingcart");

    // Cart page elements
    By shoppingCartHeading = By.xpath("//h1[text()='Shopping Cart']");
    
    // First product in home page
    By productName = By.xpath("(//a[contains(text(),'Hummingbird printed t-shirt')])[1]");
    
    // Product image in shopping cart
    By productImage = By.xpath("(//img[@alt ='Hummingbird printed t-shirt'])[1]");
 
    // Product price in shopping cart
    By productPrice = By.cssSelector(".product-line__item-price");
    
    // Minus button in shopping cart
    By minusButton = By.xpath("(//*[@id='decrement_button_1'])[1]");
    
    // Plus button in shopping cart
    By plusButton = By.xpath("(//*[@id='increment_button_1'])[1]");
    
    // Quantity in shopping cart 
    By quantityField = By.cssSelector("input.js-cart-line-product-quantity");
    
    // Remove option 
    By removeButton = By.cssSelector("a[data-link-action='delete-from-cart']");

    // Continue shopping
    By continueShopping = By.className("continue-shopping");
    
    // Proceed to checkout
    By proceedToCheckout = By.xpath("//a[contains(text(),'Proceed to checkout')]");
    
    // Order summary section
    By orderSummary = By.xpath("//*[contains(text(),'Order summary')]");
    
    // Product remove alert
    By productRemoveAlert = By.cssSelector(".js-cart-update-alert");
    
    // Cart empty message 
    By cartEmptyMessage = By.className("cart__empty");


    
    public void openCartPage() {

        // If product is not already in cart, add one product
        if (driver.findElements(cartIcon).size() > 0) {

            driver.findElement(cartIcon).click();
            
        } else {
            driver.findElement(addproductToCart).click();


        }
    }


    // SC_TC88
    @Test(priority = 1)
    public void verifyShoppingCartPage() {

        Assert.assertTrue(
                wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartHeading))
                        .isDisplayed(),
                "Shopping Cart page is not displayed"
        );

    }


    // SC_TC89
    @Test(priority = 2)
    public void verifyProductDisplayedInCart() {
    	
        Assert.assertTrue(
                wait.until(ExpectedConditions.visibilityOfElementLocated(productName))
                        .isDisplayed(),
                "Product name is not displayed"
        );

        Assert.assertTrue(
                driver.findElement(productImage).isDisplayed(),
                "Product image is not displayed"
        );

    }


    // SC_TC90
    @Test(priority = 3)
    public void verifyProductDetailsInCart() {

        String productText = driver.findElement(productName).getText();

        Assert.assertTrue(
                productText.contains("Hummingbird"),
                "Wrong product is displayed"
        );

    }


    // SC_TC91
    @Test(priority = 4)
    public void verifyProductPriceInCart() {

        String price = driver.findElement(productPrice).getText();
        Assert.assertEquals(price, "€22.94");
        
    }


    // SC_TC92
    @Test(priority = 5)
    public void verifyOrderSummary() {

        Assert.assertTrue(
                driver.findElement(orderSummary).isDisplayed(),
                "Order summary is not displayed"
        );

    }


    // SC_TC93
    @Test(priority = 6)
    public void verifyIncreaseQuantity() {

        WebElement quantity = driver.findElement(quantityField);

        String oldQuantity = quantity.getAttribute("value");

        driver.findElement(plusButton).click();

        wait.until(ExpectedConditions.not(
                ExpectedConditions.attributeToBe(quantityField, "value", oldQuantity)
        ));

        String newQuantity = quantity.getAttribute("value");

        Assert.assertNotEquals(
                oldQuantity,
                newQuantity,
                "Quantity did not increase"
        );

    }


    // SC_TC93
    @Test(priority = 7)
    public void verifyDecreaseQuantity() {

        WebElement quantity = driver.findElement(quantityField);

        String oldQuantity = quantity.getAttribute("value");

        driver.findElement(minusButton).click();

        wait.until(ExpectedConditions.not(
                ExpectedConditions.attributeToBe(quantityField, "value", oldQuantity)
        ));

        String newQuantity = quantity.getAttribute("value");

        Assert.assertNotEquals(
                oldQuantity,
                newQuantity,
                "Quantity did not decrease"
        );

    }


    // SC_TC96
    @Test(priority = 8)
    public void verifyRemoveProduct() { 

        driver.findElement(removeButton).click();

        Assert.assertTrue(
                wait.until(ExpectedConditions.visibilityOfElementLocated(productRemoveAlert))
                        .isDisplayed(),
                "Product was not removed from cart"
        );

    }

    // SC_TC98 / SC_TC99
    @Test(priority = 9)
    public void verifyProceedToCheckout() {

        // Add product again if cart is empty
        if (driver.findElements(cartEmptyMessage).size() > 0) {

            driver.findElement(addproductToCart).click();
        }

        driver.findElement(proceedToCheckout).click();
    }

}
