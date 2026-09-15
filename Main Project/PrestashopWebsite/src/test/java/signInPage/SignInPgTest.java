package signInPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import basePgm.BaseTest;

public class SignInPgTest extends BaseTest{
	
	@DataProvider(name = "userRegistrationData")
	public Object[][] userRegistrationData() {

	    return new Object[][] {

	        {"Valid Registration",
	         "Mrs.", "Aiswarya", "Test", "asdf@gmail.com",
	         "aw3se4dr5", "09/22/2002", true, true, true},

	        {"Register with an already registered email",
	         "Mrs.", "Aiswarya", "Test", "asdf@gmail.com",
	         "aw3se4dr5", "09/22/2002", true, true, false}
	    };
	}

    @DataProvider(name = "LoginData")
    public Object[][] LoginData() {
    	return new Object[][] {

            // Test Case, Email, Password, Expected Success
    		
          {"Login with registered email", "asdf@gmail.com", "aw3se4dr5", true},

          {"Login with invalid email", "df@gmail.com", "aw3se4dr5", false},
          
          {"Login with invalid password", "asdf@gmail.com", "aw3serrrr", false},
          
          {"Login with blank credentials", "", "", false},
          
          {"Login with valid email and blank password", "asdf@gmail.com", "", false}
	
    	};
    }
    
    @Test(priority = 1, dataProvider = "userRegistrationData")
    public void userRegistration(
            String testCase,
            String gender,
            String firstName,
            String lastName,
            String email,
            String password,
            String birthday,
            boolean termsCheckboxRequired,
            boolean privacyCheckboxRequired,
            boolean expectedSuccess) {

        System.out.println("Running: " + testCase);

        // Make sure we are inside PrestaShop iframe
        switchToPrestaShopFrame();

        // Click Sign in
        WebElement signIn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[contains(text(),'Sign in')]")
                )
        );
        signIn.click();

        // Click Create an account
        WebElement createAccount = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(), 'Create your account')]")
                )
        );
        createAccount.click();
        
     // =========================
        // GENDER RADIO BUTTON
        // =========================

        if (gender.equals("Mr.")) {

            WebElement mrRadio = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='field-id_gender_1']") ) );
            mrRadio.click();

            Assert.assertTrue(mrRadio.isSelected(),"Mr. radio button is not selected");

        } else if (gender.equals("Mrs.")) {

            WebElement mrsRadio = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='field-id_gender_2']") ) );
            mrsRadio.click();

            Assert.assertTrue(mrsRadio.isSelected(),"Mrs. radio button is not selected");
        }

        // Enter first name
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("firstname")
                )
        ).sendKeys(firstName);

        // Enter last name
        driver.findElement(By.name("lastname"))
                .sendKeys(lastName);

        // Enter email
        driver.findElement(By.name("email"))
                .sendKeys(email);

        // Enter password
        driver.findElement(By.name("password"))
                .sendKeys(password);

        // Terms and conditions
        WebElement checkbox1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("field-psgdpr") ) );
        
        if (termsCheckboxRequired && !checkbox1.isSelected()) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", checkbox1);
        }

        // Customer data privacy
        WebElement checkbox2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("field-customer_privacy") ) );
        
        if (privacyCheckboxRequired && !checkbox2.isSelected()) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", checkbox2);                            
        } 
        
     // Create account
        WebElement createAccountButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(., 'Create account')]") ) );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                createAccountButton
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                createAccountButton
        );


        // ==============================
        // VERIFY RESULT
        // ==============================

        if (expectedSuccess) {

            // Successful registration
            WebElement signOut = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("a.logout")
                    )
            );

            Assert.assertTrue(
                    signOut.isDisplayed(),
                    "Registration was not successful."
            );

            System.out.println("Valid Registration passed");
            

            // Sign out
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center', inline:'center'});",
                    signOut
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    signOut
            );

        } else {

            // Duplicate email
            WebElement duplicateEmailError = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[@id=\"customer-form\"]/section/div[4]/div/div")
                    )
            );

            Assert.assertTrue(
                    duplicateEmailError.isDisplayed(),
                    "Duplicate email error message was not displayed."
            );

            Assert.assertTrue(
                    duplicateEmailError.getText().contains(
                            "The email is already used, please choose another one or sign in"
                    ),
                    "Incorrect duplicate email error message."
            );

            System.out.println("Already registered email test validation passed");
        }
    }
	

	 
	 @Test(priority = 2, dataProvider = "LoginData")
	    public void userLogin(
	            String testCase,
	            String email,
	            String password,
	            boolean expectedSuccess ) {

	        System.out.println("Running: " + testCase);
	        
	     // Make sure we are inside PrestaShop iframe
	        switchToPrestaShopFrame();
	        
	     // Click Sign in from home page
	        WebElement signIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Sign in')]") ) );
	        signIn.click();
	       
	        //Enter email
	        wait.until(
	                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='field-email']") ) ).sendKeys(email);
	        
	        //Enter password
	        driver.findElement(By.xpath("//input[@id='field-password']") ).sendKeys(password);
	        
	     // Click Sign in
	        driver.findElement(By.id("submit-login") ).click();
	        
	        
        	// =========================
            // VERIFY RESULT
            // =========================

            if (expectedSuccess) {

                // Login successful → user should reach Home page
                // and Sign out should be available

                WebElement signOut = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.cssSelector("a.logout")
                        )
                );

                Assert.assertTrue(
                        signOut.isDisplayed(),
                        "Sign out option is not available after successful login."
                );

                System.out.println("LOGIN PASSED: " + testCase);


                // Scroll the element to the center of the screen
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'center'});",
                        signOut
                );

                // Click using JavaScript
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();",
                        signOut
                );

            } else {

                // Login failed → user should remain on Sign in page

                boolean stillOnSignInPage = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//input[@id='field-email']")
                        )
                ).isDisplayed();

                Assert.assertTrue(
                        stillOnSignInPage,
                        "Invalid login was accepted: " + testCase
                );

                System.out.println(
                        "LOGIN VALIDATION PASSED: " + testCase
                );
            }
        }

}
