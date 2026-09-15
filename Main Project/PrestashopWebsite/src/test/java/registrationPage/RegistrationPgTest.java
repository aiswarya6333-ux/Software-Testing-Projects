package registrationPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import basePgm.BaseTest;

public class RegistrationPgTest extends BaseTest{

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() {
    	return new Object[][] {

            // Test Case, Gender, First Name, Last Name, Email, Password,
            // Birthday, Terms, Privacy, Expected Success, Validation Type

            {"Blank Last Name",
            	"Mrs.", "Aiswarya", "", "ac@gmail.com", "aw3se4dr5",
                "09/22/2002", true, true, false},

            {"Invalid Email",
                "Mr.", "Ashwin", "Test", "asdf@g", "aw3se4dr5",
                "", true, true, false},

            {"Invalid First Name",
                "Mrs.", "12345", "Test", "aw@gmail.com", "aw3se4dr5",
                "", true, true, false},

            {"Invalid Last Name",
                "Mrs.","Aiswarya", "12345", "awse@gmail.com", "aw3se4dr5",
                "", true, true, false},

            {"Weak Password",
                "Mrs.","Aiswarya", "Test", "dr@gmail.com", "123",
                "", true, true, false},

            {"Terms Not Selected",
                "Mrs.","Aiswarya", "Test", "ft@gmail.com", "aw3se4dr5",
                "", false, true, false},

            {"Privacy Not Selected",
                "Mrs.","Aiswarya", "Test", "gy@gmail.com", "aw3se4dr5",
                "09/22/2002", true, false, false},

            {"All Mandatory Fields Blank",
                "", "", "", "", "", "",
                false, false, false}
        };
    }


    // =========================
    // REGISTRATION TEST
    // =========================

    @Test(priority = 1, dataProvider = "registrationData")
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
            boolean expectedSuccess ) {

        System.out.println("Running: " + testCase);

	        // Make sure we are inside PrestaShop iframe
	        switchToPrestaShopFrame();

	        // =========================
	        // REGISTRATION
	        // =========================

	        // Click Sign in
	        WebElement signIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Sign in')]") ) );
	        signIn.click();

	        // Click Create an account
	        WebElement createAccount = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Create your account')]") ) );
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
	        
	        // =========================
	        // REGISTRATION FIELDS
	        // =========================
	        
	        //First name
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstname") ) ).sendKeys(firstName);

	        // Last name
	        driver.findElement(By.name("lastname") ).sendKeys(lastName);

	        // Email
	        driver.findElement(By.name("email") ).sendKeys(email);

	        // Password
	        driver.findElement(By.name("password") ).sendKeys(password);
	        
	        //Birth date
	        driver.findElement(By.xpath("//input[@id='field-birthday']") ).sendKeys(birthday);

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
	        }                                                                      //subscibe option at bottom of registration pg locator:     input[aria-label='Your email address']

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
	        
	        // =========================
	        // VERIFY RESULT
	        // =========================

	        if (expectedSuccess) {

	            WebElement signOut = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.logout") ) );

	            Assert.assertTrue(signOut.isDisplayed(),"Registration was not successful.");

	            System.out.println("REGISTRATION PASSED: " + testCase);

	        } else {

	            // For invalid/blank data, account should NOT be created
	            boolean stillOnRegistrationPage =wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstname") ) ).isDisplayed();

	            Assert.assertTrue(stillOnRegistrationPage,"Invalid data was accepted: " + testCase);

	            System.out.println("VALIDATION PASSED: " + testCase);
	        }
    }
	
	

}
