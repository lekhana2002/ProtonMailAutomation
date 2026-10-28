package ProtonMailTest.AbstractComponents;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AbstarctComponents {
WebDriver driver;
WebDriverWait wait;
	
	public AbstarctComponents(WebDriver driver) {
		super();
		this.driver=driver;
		wait=new WebDriverWait(driver, Duration.ofSeconds(30));
				
	}
	
	public WebElement waitUntilTheElementIsLocated(By findby) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(findby));
		
	}
	

public void scrollToElement(WebElement element) {

    if (element == null) {
        throw new IllegalArgumentException(
            "Element is null. C"
            + "heck how the element is located."
        );
    }

    JavascriptExecutor js = (JavascriptExecutor) driver;

    js.executeScript(
        "arguments[0].scrollIntoView({block: 'center'});",
        element
    );
  
}

public void jsClick(WebElement confirmBtn) {
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmBtn);
}

public void navigateBackUntilElementAppears(By locator) {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    int attempts = 0;
    int maxAttempts = 5;

    while (driver.findElements(locator).isEmpty()
            && attempts < maxAttempts) {

        driver.navigate().back();
        attempts++;

        try {
            wait.until(
                ExpectedConditions.presenceOfElementLocated(locator)
            );
        } catch (TimeoutException e) {
            // Continue navigating back
        }
    }

    Assert.assertTrue(
        !driver.findElements(locator).isEmpty(),
        "Element did not appear after navigating back"
    );
}
	
	

}
