package checkoutAndOrderPlacement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import basePgm.BaseTest;

public class CheckoutAndOdrPlcmntTest extends BaseTest{
	
	// Cart icon in header
    By cartIcon = By.id("desktop_ps_shoppingcart");
    
    // Product on Home Page
    By addproductToCart = By.xpath("(//button[@data-button-action='add-to-cart' and contains(@title,'Hummingbird printed t-shirt')])[1]");

    public void openCheckout() {
    	
    	// If product is not already in cart, add one product
        if (driver.findElements(cartIcon).size() > 0) {

            driver.findElement(cartIcon).click();
            
        } else {
            driver.findElement(addproductToCart).click();


        }

        // Proceed to checkout
        WebElement checkout = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Proceed to checkout')]")));

        checkout.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.tagName("body")));
    }


    // ---------------------------------------------------------
    // CO_TC101
    // Verify Proceed to Checkout button works
    // ---------------------------------------------------------

    @Test(priority = 1)
    public void verifyProceedToCheckout() {

        openCheckout();

        WebElement checkoutPage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Addresses') or contains(text(),'Personal Information')]")));

        Assert.assertTrue(checkoutPage.isDisplayed(),
                "Checkout page was not displayed");
    }


    // ---------------------------------------------------------
    // CO_TC102
    // Verify checkout before user registration
    // ---------------------------------------------------------

    @Test(priority = 2)
    public void verifyGuestCheckoutNavigation() {

        openCheckout();

        WebElement personalInformation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Personal Information') or contains(text(),'Guest checkout')]")));

        Assert.assertTrue(personalInformation.isDisplayed(),
                "Personal Information page was not displayed");
    }


    // ---------------------------------------------------------
    // CO_TC103
    // Verify Personal Information page
    // ---------------------------------------------------------

    @Test(priority = 3)
    public void verifyPersonalInformationPage() {

        openCheckout();

        // First name
        WebElement firstName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("field-firstname")));

        // Last name
        WebElement lastName = driver.findElement(
                By.id("field-lastname"));

        // Email
        WebElement email = driver.findElement(
                By.id("field-email"));

        firstName.sendKeys("Aiswarya");
        lastName.sendKeys("Test");
        email.sendKeys("aiswarya12345@gmail.com");

        Assert.assertTrue(firstName.getAttribute("value").equals("Aiswarya"));
        Assert.assertTrue(lastName.getAttribute("value").equals("Test"));
        Assert.assertTrue(email.getAttribute("value").contains("@"));
    }


    // ---------------------------------------------------------
    // CO_TC104
    // Verify blank mandatory personal information fields
    // ---------------------------------------------------------

    @Test(priority = 4)
    public void verifyBlankPersonalInformationValidation() {

        openCheckout();

        // Click Continue without entering mandatory fields
        WebElement continueButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(.,'Continue')]")));

        continueButton.click();

        // Check that user remains on checkout page
        Assert.assertTrue(driver.getPageSource().contains("Personal Information")
                        || driver.getPageSource().contains("First name"),
                "Validation page was not displayed");
    }


    // ---------------------------------------------------------
    // CO_TC109
    // Verify checkout after user registration
    // ---------------------------------------------------------

    @Test(priority = 5)
    public void verifyRegisteredUserCheckout() {

        openCheckout();

        // Addresses page should be displayed for registered user
        WebElement addressPage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Addresses')]")));

        Assert.assertTrue(addressPage.isDisplayed(),
                "Addresses page was not displayed");
    }


    // ---------------------------------------------------------
    // CO_TC111
    // Verify product details in Addresses page
    // ---------------------------------------------------------

    @Test(priority = 6)
    public void verifyProductDetailsInAddressPage() {

        openCheckout();

        WebElement showDetails = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[contains(text(),'Show details')]")));

        showDetails.click();

        Assert.assertTrue(showDetails.isDisplayed(),
                "Show details option is not displayed");
    }


    // ---------------------------------------------------------
    // CO_TC112
    // Verify checkout steps
    // ---------------------------------------------------------

    @Test(priority = 7)
    public void verifyCheckoutSteps() {

        openCheckout();

        WebElement addresses = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Addresses')]")));

        Assert.assertTrue(addresses.isDisplayed(),
                "Addresses step is not displayed");
    }


    // ---------------------------------------------------------
    // CO_TC113
    // Verify mandatory fields
    // ---------------------------------------------------------

    @Test(priority = 8)
    public void verifyMandatoryFields() {

        openCheckout();

        WebElement firstNameLabel = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("label[for='field-firstname']")));

        WebElement lastNameLabel = driver.findElement(
                By.cssSelector("label[for='field-lastname']"));

        Assert.assertTrue(firstNameLabel.getText().contains("*"),
                "First name is not marked mandatory");

        Assert.assertTrue(lastNameLabel.getText().contains("*"),
                "Last name is not marked mandatory");
    }


    // ---------------------------------------------------------
    // CO_TC118
    // Verify Zip / Postal Code
    // ---------------------------------------------------------

    @Test(priority = 9)
    public void verifyPostalCodeField() {

        openCheckout();

        WebElement postalCode = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("field-postcode")));

        postalCode.sendKeys("1234");

        Assert.assertEquals(postalCode.getAttribute("value"), "1234",
                "Postal code was not entered correctly");
    }


    // ---------------------------------------------------------
    // CO_TC119
    // Verify Country dropdown
    // ---------------------------------------------------------

    @Test(priority = 10)
    public void verifyCountryDropdown() {

        openCheckout();

        WebElement country = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("field-id_country")));

        Assert.assertTrue(country.isDisplayed(),
                "Country dropdown is not displayed");
    }


    // ---------------------------------------------------------
    // CO_TC121
    // Verify product details in shipping page
    // ---------------------------------------------------------

    @Test(priority = 11)
    public void verifyProductDetailsInCheckout() {

        openCheckout();

        WebElement showDetails = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[contains(text(),'Show details')]")));

        showDetails.click();

        Assert.assertTrue(showDetails.isDisplayed());
    }


    // ---------------------------------------------------------
    // CO_TC123
    // Verify shipping cost
    // ---------------------------------------------------------

    @Test(priority = 12)
    public void verifyShippingCost() {

        openCheckout();

        WebElement shipping = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Shipping')]")));

        Assert.assertTrue(shipping.isDisplayed(),
                "Shipping cost is not displayed");
    }

    // ---------------------------------------------------------
    // CO_TC127
    // Verify payment methods
    // ---------------------------------------------------------

    @Test(priority = 13)
    public void verifyPaymentMethods() {

        openCheckout();

        WebElement bankWire = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Bank wire')]")));

        WebElement cashOnDelivery = driver.findElement(
                By.xpath("//*[contains(text(),'Cash on delivery')]"));

        WebElement check = driver.findElement(
                By.xpath("//*[contains(text(),'Check')]"));

        Assert.assertTrue(bankWire.isDisplayed(),
                "Bank Wire payment method is not displayed");

        Assert.assertTrue(cashOnDelivery.isDisplayed(),
                "Cash on Delivery payment method is not displayed");

        Assert.assertTrue(check.isDisplayed(),
                "Check payment method is not displayed");
    }


    // ---------------------------------------------------------
    // CO_TC131
    // Verify only one payment method can be selected
    // ---------------------------------------------------------

    @Test(priority = 14)
    public void verifyOnlyOnePaymentMethodSelected() {

        openCheckout();

        WebElement bankWire = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[contains(text(),'Bank wire')]")));

        WebElement cashOnDelivery = driver.findElement(
                By.xpath("//*[contains(text(),'Cash on delivery')]"));

        bankWire.click();

        cashOnDelivery.click();

        // Find payment radio buttons
        int selectedCount = 0;

        for (WebElement radio : driver.findElements(
                By.cssSelector("input[type='radio']"))) {

            if (radio.isSelected()) {
                selectedCount++;
            }
        }

        Assert.assertEquals(selectedCount, 1,
                "More than one payment method is selected");
    }


    // ---------------------------------------------------------
    // CO_TC132
    // Verify Terms of Service
    // ---------------------------------------------------------

    @Test(priority = 15)
    public void verifyTermsAndConditions() {

        openCheckout();

        WebElement terms = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("conditions_to_approve[terms-and-conditions]")));

        Assert.assertTrue(terms.isDisplayed(),
                "Terms and conditions checkbox is not displayed");
    }


    // ---------------------------------------------------------
    // CO_TC133
    // Verify order total
    // ---------------------------------------------------------

    @Test(priority = 16)
    public void verifyOrderTotal() {

        openCheckout();

        WebElement total = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".cart-total .value")));

        Assert.assertTrue(total.isDisplayed(),
                "Order total is not displayed");
    }


    // ---------------------------------------------------------
    // CO_TC134
    // Verify Back to Shipping button
    // ---------------------------------------------------------

    @Test(priority = 17)
    public void verifyBackToShipping() {

        openCheckout();

        WebElement backToShipping = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(),'Back to shipping')]")));

        backToShipping.click();

        WebElement shipping = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Shipping')]")));

        Assert.assertTrue(shipping.isDisplayed(),
                "Shipping page was not displayed");
    }


    // ---------------------------------------------------------
    // CO_TC135
    // Verify Terms of Service is clickable
    // ---------------------------------------------------------

    @Test(priority = 18)
    public void verifyTermsLink() {

        openCheckout();

        WebElement termsLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[contains(text(),'terms and conditions')]")));

        termsLink.click();

        Assert.assertTrue(driver.getPageSource().contains("Terms"),
                "Terms and Conditions window was not displayed");
    }


    // ---------------------------------------------------------
    // CO_TC137
    // Verify Order Confirmation page
    // ---------------------------------------------------------

    @Test(priority = 19)
    public void verifyOrderConfirmationPage() {

        openCheckout();

        // Select Bank Wire
        WebElement bankWire = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[contains(text(),'Bank wire')]")));

        bankWire.click();

        // Select terms
        WebElement terms = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("conditions_to_approve[terms-and-conditions]")));

        if (!terms.isSelected()) {
            terms.click();
        }

        // Place order
        WebElement placeOrder = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(.,'Place order')]")));

        placeOrder.click();

        // Verify confirmation
        WebElement confirmation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Your order is confirmed')]")));

        Assert.assertTrue(confirmation.isDisplayed(),
                "Order confirmation message was not displayed");
    }

}
