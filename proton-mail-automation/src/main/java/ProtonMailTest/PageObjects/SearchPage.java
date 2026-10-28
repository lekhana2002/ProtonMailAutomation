package ProtonMailTest.PageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {

    WebDriver driver;
    WebDriverWait wait;

    By searchBox = By.cssSelector("[data-testid='search-keyword']");
    By anyMail = By.xpath("//span[@data-testid='message-row:subject']");
    By searchInputBox=By.id("search-keyword");
    By clearSearch=By.xpath("//button[@data-testid='advanced-search:clear']");

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    
    public void clearSearchText() {

        WebElement input = wait.until(
            ExpectedConditions.visibilityOfElementLocated(searchInputBox)
        );

        String text = input.getAttribute("value");

        if (text != null && !text.trim().isEmpty()) {

            wait.until(
                ExpectedConditions.elementToBeClickable(clearSearch)
            ).click();

            System.out.println("Search text cleared successfully");

        } else {
            System.out.println("Search box is already empty");
        }
    }

    public void searchMail(String keyword) throws InterruptedException {

        WebElement box = wait.until(
            ExpectedConditions.presenceOfElementLocated(searchBox)
        );

        box.click();
        
        clearSearchText();
        
        
        WebElement input = wait.until(
                ExpectedConditions.presenceOfElementLocated(searchInputBox)
            );


  
        Thread.sleep(3000);
        input.sendKeys(keyword);
        input.sendKeys(Keys.ENTER);
    }
 

    public boolean isMailPresent(String subject) {
        return !driver.findElements(By.xpath(
            "//span[@data-testid='message-row:subject' and @title='" + subject + "']")).isEmpty();
    }

    public int mailCount() {
        return driver.findElements(anyMail).size();
    }
}