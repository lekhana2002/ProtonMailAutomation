package ProtonMailTest.PageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class InboxAndMessageActionsPage {
	WebDriver driver;
    WebDriverWait wait;

    public InboxAndMessageActionsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }
    
    

        // ---------- Folders ----------
        @FindBy(xpath = "//a[@title='Inbox']")
        WebElement inboxFolder;

        @FindBy(xpath = "//a[@title='Starred']")
        WebElement starredFolder;

        @FindBy(xpath = "//a[.//*[normalize-space()='Archive'] or @title='Archive']")
        WebElement archiveFolder;

        @FindBy(xpath = "//a[.//*[normalize-space()='Trash'] or @title='Trash']")
        WebElement trashFolder;

        @FindBy(xpath = "//button[contains(@data-testid,'star') or contains(@title,'Star') or contains(@aria-label,'Star')]")
        WebElement starBtn;

        @FindBy(xpath = "//button[contains(@data-testid,'unread') or contains(@title,'unread') or contains(@aria-label,'unread')]")
        WebElement unreadBtn;

        @FindBy(xpath = "//button[contains(.,'More')]")
        WebElement moreBtn;
        // ---------- Toolbar buttons (GUESSES: check with Inspect if they time out) ----------
        @FindBy(xpath = "//button[@data-testid='toolbar:movetotrash']")
        WebElement trashBtn;

        @FindBy(xpath = "//button[@data-testid='toolbar:movetoarchive']")
        WebElement archiveBtn;

        @FindBy(xpath = "//button[@data-testid='toolbar:movetoinbox']")
        WebElement moveToInboxBtn;

        // ---------- Locators that depend on the subject ----------
        private By subjectOf(String subject) {
            return By.xpath("//span[@data-testid='message-row:subject' and @title='" + subject + "']");
        }

        private By rowOf(String subject) {
            return By.xpath("//div[@data-shortcut-target='item-container'][.//span[@title='" + subject + "']]");
        }

        private By checkboxOf(String subject) {
            return By.xpath("//div[@data-shortcut-target='item-container'][.//span[@title='" + subject
                    + "']]//input[@type='checkbox']");
        }

        // ---------- Open folders ----------
        public void openInbox() {
            wait.until(ExpectedConditions.elementToBeClickable(inboxFolder)).click();
            wait.until(ExpectedConditions.urlContains("inbox"));
        }

        public void openStarred() {
            wait.until(ExpectedConditions.elementToBeClickable(starredFolder)).click();
            wait.until(ExpectedConditions.urlContains("starred"));
            driver.navigate().refresh();
        }

        private void expandMore() {
            try {
                moreBtn.click();
            } catch (Exception e) {
                // already expanded
            }
        }

        public void openArchive() {
            expandMore();
            wait.until(ExpectedConditions.elementToBeClickable(archiveFolder)).click();
            wait.until(ExpectedConditions.urlContains("archive"));
        }

        public void openTrash() {
            expandMore();
            wait.until(ExpectedConditions.elementToBeClickable(trashFolder)).click();
            wait.until(ExpectedConditions.urlContains("trash"));
        }

        // ---------- Open a mail ----------
        public void openMessage(String subject) {
            WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(subjectOf(subject)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", row);
            wait.until(ExpectedConditions.elementToBeClickable(archiveBtn));   // toolbar ready
        }

        // ---------- Actions ----------
        public void markUnread() { wait.until(ExpectedConditions.elementToBeClickable(unreadBtn)).click(); }
        
        public void star(String subject) {
            By starOff = By.xpath("//div[@data-shortcut-target='item-container'][.//span[@title='" + subject
                    + "']]//button[@data-testid='item-star-false']");
            WebElement star = wait.until(ExpectedConditions.presenceOfElementLocated(starOff));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", star);

            By starOn = By.xpath("//div[@data-shortcut-target='item-container'][.//span[@title='" + subject
                    + "']]//button[@data-testid='item-star-true']");
            wait.until(ExpectedConditions.presenceOfElementLocated(starOn));
        }
        
        public void archive()    { wait.until(ExpectedConditions.elementToBeClickable(archiveBtn)).click(); }
        public void moveToTrash(){ wait.until(ExpectedConditions.elementToBeClickable(trashBtn)).click(); }
        public void restore()    { wait.until(ExpectedConditions.elementToBeClickable(moveToInboxBtn)).click(); }

        // ---------- Bulk ----------
        public void selectMessage(String subject) {
            WebElement box = wait.until(ExpectedConditions.presenceOfElementLocated(checkboxOf(subject)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", box);
        }

        // ---------- Check the resulting state ----------
        public boolean isMailPresent(String subject) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(subjectOf(subject)));
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public boolean isMailGone(String subject) {
            try {
                return new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.invisibilityOfElementLocated(subjectOf(subject)));
            } catch (Exception e) {
                return false;
            }
        }

        public String getRowClass(String subject) {
            return wait.until(ExpectedConditions.presenceOfElementLocated(rowOf(subject))).getAttribute("class");
        }

        public boolean isUnread(String subject) {
            return getRowClass(subject).toLowerCase().contains("unread");
        }
        
        public boolean waitForMail(String subject) {
            for (int i = 0; i < 6; i++) {
                driver.navigate().refresh();
                try {
                    new WebDriverWait(driver, Duration.ofSeconds(8))
                        .until(ExpectedConditions.presenceOfElementLocated(subjectOf(subject)));
                    return true;
                } catch (Exception e) {
                    // not there yet, try again
                }
            }
            return false;
        }

		
    }
    


