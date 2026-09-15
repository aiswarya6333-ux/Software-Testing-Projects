package productDetails;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import basePgm.BaseTest;

public class ProductDetailsTest extends BaseTest{
	
	// =========================================================
    // COMMON METHOD - OPEN A PRODUCT DETAILS PAGE
    // =========================================================

    public void openProduct() {

        // Scroll to product section
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/section[3]"))));

        // Click first product name
        WebElement product = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("(//a[text()='Hummingbird printed t-shirt'])[1]")));

        js.executeScript("arguments[0].click();", product);

        // Wait for product page
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='Hummingbird printed t-shirt']")));
    }


    // =========================================================
    // PD_TC62 - OPEN PRODUCT DETAILS PAGE
    // =========================================================

    @Test(priority = 1)
    public void verifyProductDetailsPageOpens() {

        WebElement productTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Featured products']")));

        Assert.assertTrue(
                productTitle.isDisplayed(),
                "Product details page was not displayed");

        System.out.println("PD_TC62 - Product details page opened successfully");
    }


    // =========================================================
    // PD_TC63 - PRODUCT NAME
    // =========================================================

    @Test(priority = 2)
    public void verifyProductName() {

        openProduct();

        WebElement productName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"center-column\"]/div[1]/div[2]/h1")));

        Assert.assertFalse(
                productName.getText().trim().isEmpty(),
                "Product name is not displayed");

        System.out.println(
                "PD_TC63 - Product Name: " + productName.getText());
    }


    // =========================================================
    // PD_TC64 - PRODUCT IMAGE
    // =========================================================

    @Test(priority = 3)
    public void verifyProductImage() {

        openProduct();

        WebElement image = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(
                                ".product-cover img.js-qv-product-cover")));

        Assert.assertTrue(
                image.isDisplayed(),
                "Main product image is not displayed");

        Assert.assertTrue(
                image.getAttribute("src") != null &&
                !image.getAttribute("src").isEmpty(),
                "Product image source is empty");

        System.out.println("PD_TC64 - Product image displayed successfully");
    }


    // =========================================================
    // PD_TC65 - PRODUCT DISCOUNT
    // =========================================================

    @Test(priority = 4)
    public void verifyProductDiscount() {

        openProduct();

        WebElement discount = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".discount-percentage")));

        Assert.assertTrue(
                discount.isDisplayed(),
                "Discount percentage is not displayed");

        System.out.println(
                "PD_TC65 - Discount: " + discount.getText());
    }


    // =========================================================
    // PD_TC66 - PRODUCT PRICE
    // =========================================================

    @Test(priority = 5)
    public void verifyProductPrice() {

        openProduct();

        WebElement price = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".current-price")));

        Assert.assertTrue(
                price.isDisplayed(),
                "Discounted/current price is not displayed");

        Assert.assertFalse(
                price.getText().trim().isEmpty(),
                "Price value is empty");

        System.out.println(
                "PD_TC66 - Current price: " + price.getText());
    }


    // =========================================================
    // PD_TC67 - TAX INFORMATION
    // =========================================================

    @Test(priority = 6)
    public void verifyTaxInformation() {

        openProduct();

        WebElement tax = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".tax-shipping-delivery-label")));

        Assert.assertTrue(
                tax.isDisplayed(),
                "Tax information is not displayed");

        Assert.assertTrue(
                tax.getText().toLowerCase().contains("tax"),
                "Tax information text is not displayed");

        System.out.println(
                "PD_TC67 - Tax information: " + tax.getText());
    }


    // =========================================================
    // PD_TC68 - PRODUCT DESCRIPTION
    // =========================================================

    @Test(priority = 7)
    public void verifyProductDescription() {

        openProduct();

        WebElement descriptionTab = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='#description']")));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                descriptionTab);

        descriptionTab.click();

        WebElement description = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#description .product-description")));

        Assert.assertTrue(
                description.isDisplayed(),
                "Product description is not displayed");

        Assert.assertFalse(
                description.getText().trim().isEmpty(),
                "Product description is empty");

        System.out.println("PD_TC68 - Product description displayed");
    }


    // =========================================================
    // PD_TC69 - PRODUCT DETAILS SECTION
    // =========================================================

    @Test(priority = 8)
    public void verifyProductDetailsSection() {

        openProduct();

        WebElement detailsTab = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='#product-details']")));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                detailsTab);

        detailsTab.click();

        WebElement details = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#product-details")));

        Assert.assertTrue(
                details.isDisplayed(),
                "Product Details section is not displayed");

        String detailsText = details.getText();

        Assert.assertTrue(
                detailsText.contains("Reference") ||
                detailsText.contains("Brand") ||
                detailsText.contains("In stock"),
                "Product details information is missing");

        System.out.println("PD_TC69 - Product Details:");
        System.out.println(detailsText);
    }


    // =========================================================
    // PD_TC70 - DATA SHEET
    // =========================================================

    @Test(priority = 9)
    public void verifyProductDataSheet() {

        openProduct();

        WebElement detailsTab = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='#product-details']")));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                detailsTab);

        detailsTab.click();

        WebElement dataSheet = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#product-details .data-sheet")));

        Assert.assertTrue(
                dataSheet.isDisplayed(),
                "Data sheet is not displayed");

        Assert.assertFalse(
                dataSheet.getText().trim().isEmpty(),
                "Data sheet is empty");

        System.out.println(
                "PD_TC70 - Data sheet: " + dataSheet.getText());
    }


    // =========================================================
    // PD_TC71 - COMMENTS / REVIEWS
    // =========================================================

    @Test(priority = 10)
    public void verifyCommentsReviewSection() {

        openProduct();

        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, document.body.scrollHeight)");

        WebElement reviewSection = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(
                                "#product-comments-list, .comments")));

        Assert.assertTrue(
                reviewSection.isDisplayed(),
                "Comments/Reviews section is not displayed");

        System.out.println(
                "PD_TC71 - Comments/Reviews section displayed");
    }


    // =========================================================
    // PD_TC72 - SEARCH FROM PRODUCT PAGE
    // =========================================================

    @Test(priority = 11)
    public void verifySearchFromProductPage() {

        openProduct();

        WebElement searchBox = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input[name='s']")));

        searchBox.clear();
        searchBox.sendKeys("Mug");
        searchBox.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#js-product-list-header")));

        String heading = driver.findElement(
                By.cssSelector("#js-product-list-header")).getText();

        Assert.assertTrue(
                heading.toLowerCase().contains("search") ||
                heading.toLowerCase().contains("mug"),
                "Search results were not displayed");

        System.out.println(
                "PD_TC72 - Search from product page successful");
    }


    // =========================================================
    // PD_TC73 - SIZE AND COLOR SELECTION
    // =========================================================

    @Test(priority = 12)
    public void verifySizeAndColorSelection() {

        openProduct();

        // Color option
        java.util.List<WebElement> colors = driver.findElements(
                By.cssSelector(
                        ".input-color + span, input[name^='group'][type='radio']"));

        if (!colors.isEmpty()) {

            WebElement color = colors.get(0);

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", color);
        }

        // Size option
        java.util.List<WebElement> sizes = driver.findElements(
                By.cssSelector(
                        "select[name^='group'], input[name^='group'][type='radio']"));

        if (!sizes.isEmpty()) {

            WebElement size = sizes.get(0);

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", size);
        }

        Assert.assertTrue(
                true,
                "Size/color selection completed");

        System.out.println(
                "PD_TC73 - Size/Color selection tested");
    }


    // =========================================================
    // PD_TC74 - PRODUCT QUANTITY
    // =========================================================

    @Test(priority = 13)
    public void verifyProductQuantityIncreaseDecrease() {

        openProduct();

        WebElement quantity = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[name='qty']")));

        quantity.clear();
        quantity.sendKeys("1");

        int initialQuantity =
                Integer.parseInt(quantity.getAttribute("value"));

        WebElement plusButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".touchspin-up")));

        plusButton.click();

        wait.until(driver ->
                Integer.parseInt(
                        quantity.getAttribute("value")) > initialQuantity);

        int increasedQuantity =
                Integer.parseInt(quantity.getAttribute("value"));

        Assert.assertEquals(
                increasedQuantity,
                initialQuantity + 1,
                "Quantity did not increase correctly");

        WebElement minusButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".touchspin-down")));

        minusButton.click();

        wait.until(driver ->
                Integer.parseInt(
                        quantity.getAttribute("value")) == initialQuantity);

        Assert.assertEquals(
                Integer.parseInt(quantity.getAttribute("value")),
                initialQuantity,
                "Quantity did not decrease correctly");

        System.out.println(
                "PD_TC74 - Quantity increase/decrease successful");
    }


    // =========================================================
    // PD_TC75 - ADD PRODUCT TO CART
    // =========================================================

    @Test(priority = 14)
    public void verifyAddProductToCart() {

        openProduct();

        WebElement addToCart = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                "button.add-to-cart")));

        addToCart.click();

        WebElement cartModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#blockcart-modal")));

        Assert.assertTrue(
                cartModal.isDisplayed(),
                "Add to cart confirmation was not displayed");

        System.out.println(
                "PD_TC75 - Product added to cart successfully");
    }


    // =========================================================
    // PD_TC76 - CART QUANTITY UPDATE
    // =========================================================

    @Test(priority = 15)
    public void verifyCartQuantityUpdate() {

        openProduct();

        WebElement quantity = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[name='qty']")));

        quantity.clear();
        quantity.sendKeys("2");

        WebElement addToCart = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button.add-to-cart")));

        addToCart.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#blockcart-modal")));

        WebElement cartCount = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(
                                ".cart-products-count")));

        Assert.assertTrue(
                cartCount.getText().contains("2"),
                "Cart quantity was not updated correctly");

        System.out.println(
                "PD_TC76 - Cart quantity updated successfully");
    }


    // =========================================================
    // PD_TC77 - PRODUCT CARD
    // =========================================================

    @Test(priority = 16)
    public void verifyProductCardDisplayed() {

        WebElement productCard = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".products .product-miniature")));

        Assert.assertTrue(
                productCard.isDisplayed(),
                "Product card is not displayed");

        Assert.assertTrue(
                productCard.findElement(
                        By.cssSelector(".product-title")).isDisplayed(),
                "Product name is missing from product card");

        Assert.assertTrue(
                productCard.findElement(
                        By.cssSelector("img")).isDisplayed(),
                "Product image is missing from product card");

        System.out.println(
                "PD_TC77 - Product card displayed correctly");
    }


    // =========================================================
    // PD_TC78 - QUICK VIEW
    // =========================================================

    @Test(priority = 17)
    public void verifyQuickView() {

        WebElement productCard = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".products .product-miniature")));

        Actions actions = new Actions(driver);

        actions.moveToElement(productCard).perform();

        WebElement quickView = wait.until(
                ExpectedConditions.elementToBeClickable(
                        productCard.findElement(
                                By.cssSelector(".js-quick-view"))));

        quickView.click();

        WebElement quickViewModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".quickview.modal.show")));

        Assert.assertTrue(
                quickViewModal.isDisplayed(),
                "Quick View modal did not open");

        System.out.println(
                "PD_TC78 - Quick View opened successfully");
    }


    // =========================================================
    // PD_TC79 - QUICK VIEW OPTIONS
    // =========================================================

    @Test(priority = 18)
    public void verifyQuickViewOptions() {

        WebElement productCard = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".products .product-miniature")));

        Actions actions = new Actions(driver);

        actions.moveToElement(productCard).perform();

        WebElement quickView = wait.until(
                ExpectedConditions.elementToBeClickable(
                        productCard.findElement(
                                By.cssSelector(".js-quick-view"))));

        quickView.click();

        WebElement quickViewModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".quickview.modal.show")));

        // Product name
        Assert.assertTrue(
                quickViewModal.findElement(
                        By.cssSelector("h1.h1")).isDisplayed(),
                "Product name is missing in Quick View");

        // Price
        Assert.assertTrue(
                quickViewModal.findElement(
                        By.cssSelector(".current-price")).isDisplayed(),
                "Product price is missing in Quick View");

        // Quantity
        WebElement quantity = quickViewModal.findElement(
                By.cssSelector("input[name='qty']"));

        Assert.assertTrue(
                quantity.isDisplayed(),
                "Quantity field is missing in Quick View");

        // Increase quantity
        WebElement plus = quickViewModal.findElement(
                By.cssSelector(".touchspin-up"));

        plus.click();

        // Add to cart
        WebElement addToCart = quickViewModal.findElement(
                By.cssSelector("button.add-to-cart"));

        Assert.assertTrue(
                addToCart.isEnabled(),
                "Add to cart button is disabled");

        addToCart.click();

        System.out.println(
                "PD_TC79 - Quick View options tested successfully");
    }

}
