package ProtonMailTest.PageObjects;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class InboxPage extends LoginPage  {
	WebDriver driver;
	WebDriverWait wait;
	
	    public InboxPage(WebDriver driver) {
	    	super(driver);
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        wait=new WebDriverWait(driver, Duration.ofSeconds(40));
	            
	    }
	    
	
	  @FindBy (xpath="//span[@class=\"m-auto\"and text()='L']")
	  WebElement ProfileIcon;
	  
	  By inboxHeading=By.xpath("//h2[@title='Inbox']");
	  By profileSignOut= By.cssSelector("button[type='submit']");

	    By ConfirmSignoutBtn = By.xpath("//dialog//button[normalize-space()='Sign out']");

	  By composeBtn = By.cssSelector("[data-testid='sidebar:compose']");
	  By messageboxverify =By.xpath("//*[@id=\"composer-title-composer-0\"]/span[2]");
	  By toField = By.cssSelector("[data-testid='composer:to']");
	  By ccButton  = By.xpath("//button[normalize-space()='CC' or normalize-space()='Cc']");
	  By bccButton = By.xpath("//button[normalize-space()='BCC' or normalize-space()='Bcc']");
	  By ccField  = By.cssSelector("input[data-testid='composer:to-cc']");
	  By bccField = By.cssSelector("input[data-testid='composer:to-bcc']");
	  By subjectField = By.cssSelector("[data-testid='composer:subject']");
	  By bodyIframe = By.cssSelector("iframe[data-testid='rooster-iframe']");

	  @FindBy(xpath= "//div[@id='rooster-editor']/div[2]")
	    WebElement bodyEditor;
	  By sendBtn= By.cssSelector("[data-testid='composer:send-button']");
	  By sentMessage= By.xpath("//*[contains(text(),'Message sent')]");
	  By draftsFolder = By.xpath("//*[normalize-space()='Drafts']");
	  By closeSettingwindow = By.xpath("//button[@data-testid='drawer-app-header:close']");
	  
	   public void GotoInboxpage(String emailid, String pwd) throws InterruptedException{
		   
		   System.out.println("Page Title: " + driver.getTitle());
		   System.out.println("current url: " + driver.getCurrentUrl());
		   login(emailid, pwd);
		   System.out.println("current url: " + driver.getCurrentUrl());
		   
		   wait.until(ExpectedConditions.visibilityOfElementLocated(inboxHeading));
		   Thread.sleep(3000);
		   String InboxpageTitle=driver.getTitle().toLowerCase();
		   System.out.println("Page Title: "+ InboxpageTitle);
		   Assert.assertTrue(InboxpageTitle.contains("inbox"));
		     
	  }
 
public void signout() {

    // Step 1: Close the compose window if it is open
    By composer = By.cssSelector(".composer-light-field-container");

    if (!driver.findElements(composer).isEmpty()) {

        By closeButton = By.cssSelector(
            "[data-testid='composer:close-button']"
        );

        try {
            WebElement close = new WebDriverWait(
                driver, Duration.ofSeconds(5)
            ).until(
                ExpectedConditions.elementToBeClickable(closeButton)
            );

            close.click();

            wait.until(
                ExpectedConditions.invisibilityOfElementLocated(composer)
            );

        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println(
                "Compose window close button was not available."
            );
        }
    }

    // Step 2: Click the account dropdown
    By accountDropdown =
        By.cssSelector("[data-testid='heading:userdropdown']");

    wait.until(
        ExpectedConditions.elementToBeClickable(accountDropdown)
    ).click();

    // Step 3: Click Sign out
    By signOutOption =
        By.xpath("//button[normalize-space()='Sign out']");

    wait.until(
        ExpectedConditions.elementToBeClickable(signOutOption)
    ).click();

    // Step 4: Confirm sign out
    wait.until(
        ExpectedConditions.elementToBeClickable(ConfirmSignoutBtn)
    ).click();

    // Step 5: Verify sign-in page
    wait.until(
        ExpectedConditions.urlContains("account.proton.me")
    );

    System.out.println("Sign out completed successfully.");
}


	  public void composeAndSend(String to,String cc, String bcc, String subject, String body) {
		    
		    wait.until(ExpectedConditions.elementToBeClickable(composeBtn)).click();
            
		    Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated
		    		(messageboxverify)).isDisplayed(),"Compose window did not open");
		    
		    WebElement toEl = wait.until(ExpectedConditions.elementToBeClickable(toField));
		    toEl.sendKeys(to);
		    toEl.sendKeys(Keys.ENTER); 
		    wait.until(ExpectedConditions.elementToBeClickable(ccButton)).click();
		    WebElement ccEl = wait.until(ExpectedConditions.elementToBeClickable(ccField));
		    ccEl.sendKeys(cc);
		    ccEl.sendKeys(Keys.ENTER);
		    wait.until(ExpectedConditions.elementToBeClickable(bccButton)).click();
		    WebElement bccEl = wait.until(ExpectedConditions.elementToBeClickable(bccField));
		    bccEl.sendKeys(bcc);
		    bccEl.sendKeys(Keys.ENTER);
		    wait.until(ExpectedConditions.elementToBeClickable(subjectField)).sendKeys(subject);
		    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(bodyIframe));
		    wait.until(ExpectedConditions.elementToBeClickable(bodyEditor)).sendKeys(body);
		    driver.switchTo().defaultContent();
		    wait.until(ExpectedConditions.elementToBeClickable(sendBtn)).click();
		}

		public boolean isMailSent() {
		    wait.until(ExpectedConditions.visibilityOfElementLocated(sentMessage));
			return true;
		}
		



public void closesettingswindow() {
    try {
        WebElement closeButton = new WebDriverWait(
            driver, Duration.ofSeconds(3)
        ).until(
            ExpectedConditions.elementToBeClickable(closeSettingwindow)
        );

        closeButton.click();
        System.out.println("Settings window closed.");

    } catch (org.openqa.selenium.TimeoutException e) {
        System.out.println(
            "Settings window is already closed. Continuing..."
        );
    }
}


	  
}
