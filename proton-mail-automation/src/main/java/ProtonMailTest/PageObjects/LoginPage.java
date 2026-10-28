package ProtonMailTest.PageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ProtonMailTest.AbstractComponents.AbstarctComponents;

public class LoginPage extends AbstarctComponents {
	WebDriver driver;
	WebDriverWait wait;
	    By SignInHeading= By.xpath("//h1[text()='Sign in']");
	    By email = By.id("username");
	    By Continue= By.xpath("//button[text()='Continue']");
	    By password = By.xpath("//input[@data-testid='input-input-element']");
	    By loginButton = By.cssSelector(
	    	    "form[data-testid='login-form'] button[type='submit']"
	    	);
	
	    By errorMessage = By.cssSelector("[role='alert']");

	    public LoginPage(WebDriver driver) {
	    	super(driver);
	        this.driver = driver;
	        this.wait = new WebDriverWait(
	            driver, Duration.ofSeconds(30));
	    }

	    public boolean isErrorDisplayed() {
	        List<WebElement> errors = driver.findElements(errorMessage);

	        for (WebElement error : errors) {
	            if (error.isDisplayed()
	                    && !error.getText().trim().isEmpty()) {
	                return true;
	            }
	        }

	        return false;
	    }
	    
	    public void login(String email, String password) {

	        WebDriverWait wait =
	                new WebDriverWait(driver, Duration.ofSeconds(30));

	        // Enter email/username
	        WebElement username = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.id("username")
	                )
	        );
	        username.clear();
	        username.sendKeys(email);
	        wait.until(
	                ExpectedConditions.visibilityOfElementLocated(Continue)).click();;
	                        

	        // Enter password
	        WebElement passwordField = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.id("password")
	                )
	        );
	        passwordField.clear();
	        passwordField.sendKeys(password);

	        // Click the submit button
	        WebElement submitButton = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.cssSelector("button[type='submit']")
	                )
	        );
	        submitButton.click();
	    }
	    
	}

