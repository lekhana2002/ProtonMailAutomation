
package ProtonMailTest.PageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DraftPage {

    WebDriver driver;
    WebDriverWait wait;

    public DraftPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        PageFactory.initElements(driver, this);
    }

    // ---------- Locators ----------
    @FindBy(css = "[data-testid='sidebar:compose']")
    WebElement composeBtn;

    @FindBy(css = "[data-testid='composer:to']")
    WebElement toField;

    @FindBy(css = "[data-testid='composer:subject']")
    WebElement subjectField;

    @FindBy(css = "iframe[data-testid='rooster-iframe']")
    WebElement bodyIframe;

    @FindBy(css = "#rooster-editor")
    WebElement bodyEditor;


    @FindBy(css = "[data-testid='composer:close-button']")
    WebElement closeBtn;

    @FindBy(xpath = "//a[@title='Drafts']")
    WebElement draftsFolder;

    @FindBy(xpath = "//a[@title='Sent']")
    WebElement sentFolder;

    @FindBy(css = "[data-testid='composer:send-button']")
    WebElement sendBtn;

    @FindBy(xpath = "//button[@data-testid='composer:delete-draft-button']")
    WebElement discardBtn;

    @FindBy(xpath = "//button[text()='Delete']")
    WebElement confirmBtn;
  
    private By mailBySubject(String subject) {
        return By.xpath("//span[@data-testid='message-row:subject' and @title='" + subject + "']");
    }

    public void ensureInboxIsOpen() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized");
        }

        driver.get("https://mail.proton.me/u/0/inbox");

        new WebDriverWait(driver, Duration.ofSeconds(30))
            .until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='sidebar:compose']")
            ));
        
        
    }
    
    public void createDraft(String to, String subject, String body) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(composeBtn)).click();

        wait.until(ExpectedConditions.elementToBeClickable(toField));
        toField.sendKeys(to);
        toField.sendKeys(Keys.ENTER);

        subjectField.sendKeys(subject);

        driver.switchTo().frame(bodyIframe);
        bodyEditor.sendKeys(body);
        driver.switchTo().defaultContent();

        Thread.sleep(8000);   // wait for auto-save
    }

    public void closeComposer() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
        Thread.sleep(2000);
    }
    public void openDrafts() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(draftsFolder)).click();
        wait.until(ExpectedConditions.urlContains("drafts"));
        driver.navigate().refresh();
        Thread.sleep(4000);   // let the list load (it may be empty)
    }

    public void openSent() {
        wait.until(ExpectedConditions.elementToBeClickable(sentFolder)).click();
        wait.until(ExpectedConditions.urlContains("sent"));
        try {
            Thread.sleep(2000);   // let the list load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Check if a mail with this subject is in the list
    public boolean isMailPresent(String subject) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(mailBySubject(subject)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Check that a mail is NOT in the list
    public boolean isMailGone(String subject) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(mailBySubject(subject)));
    }

    // Open a draft by clicking its subject in the list
    public void openDraft(String subject) {
        WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(mailBySubject(subject)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", row);
        wait.until(ExpectedConditions.visibilityOf(subjectField));
    }

    // Type extra text in the body
    public void addToBody(String text) throws InterruptedException {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(bodyIframe));
        bodyEditor.sendKeys(text);
        driver.switchTo().defaultContent();
        Thread.sleep(8000);   // wait for auto-save
    }

    // Read the body text
    public String getBodyText() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(bodyIframe));
        String text = bodyEditor.getText();
        driver.switchTo().defaultContent();
        return text;
    }

    // Delete the open draft
    public void discardDraft() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(discardBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmBtn)).click();
        Thread.sleep(3000);
    }

    // Send the open draft
    public void sendDraft() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(sendBtn)).click();
        Thread.sleep(3000);
    }
    
 // Upload attachment
    public void uploadAttachment(String filePath) {

        WebElement fileInput = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[type='file']")
            )
        );

        fileInput.sendKeys(filePath);
    }
 // Verify attachment upload
    public boolean verifyAttachmentUploaded() {

        try {
            By attachment = By.xpath(
                "//*[contains(normalize-space(.),'file attached')]"
            );

            wait.until(
                ExpectedConditions.visibilityOfElementLocated(attachment)
            );

            return true;

        } catch (Exception e) {
            return false;
        }
    }
    
 // Click Undo after sending an email

public void clickUndo() {
    By undoButton = By.xpath(
        "//*[self::button or self::a or @role='button']" +
        "[contains(normalize-space(.), 'Undo')]"
    );

    wait.until(
        ExpectedConditions.elementToBeClickable(undoButton)
    ).click();
}


    // Verify that the email composer is open
    public boolean verifyComposerOpen() {
        try {
            return wait.until(
                ExpectedConditions.visibilityOf(subjectField)
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    
}
