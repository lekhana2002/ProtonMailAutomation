package ProtonMailTest;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ProtonMailTest.PageObjects.DraftPage;
import ProtonMailTest.PageObjects.FilterPage;
import ProtonMailTest.PageObjects.InboxAndMessageActionsPage;
import ProtonMailTest.PageObjects.InboxPage;
import ProtonMailTest.PageObjects.LandingPage;
import ProtonMailTest.PageObjects.LoginPage;
import ProtonMailTest.PageObjects.SearchPage;

public class ProtonMailFuntionalTesting {

	WebDriver driver;
	WebDriverWait wait;
	LandingPage landingpage;
	LoginPage loginpage;
	InboxPage inboxpage;
	DraftPage draftspage;
	InboxAndMessageActionsPage indexandmessageactionpage;
	SearchPage searchpage;
	FilterPage filterpage;
	
	

	 @BeforeClass
	 public void setup() {
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();

	        landingpage = new LandingPage(driver);
	        loginpage = new LoginPage(driver);
	        inboxpage =new InboxPage(driver);
	        draftspage = new DraftPage(driver);
	        indexandmessageactionpage = new InboxAndMessageActionsPage(driver);
	        searchpage = new SearchPage(driver);
	        filterpage=new FilterPage(driver);
	        wait=new WebDriverWait(driver, Duration.ofSeconds(40));
	    }

	    @Test(priority = 1)
	    public void TC1_NavigateToProtonMail() {
	        landingpage.GoTo();

	        Assert.assertTrue(
	            driver.getTitle().toLowerCase().contains("proton"),
	            "Proton Mail page did not open"
	        );
	    }

@Test(priority = 2, dependsOnMethods = {"TC1_NavigateToProtonMail"})
public void TC2_OpenLoginPage() {

    // Open the login page
    driver.get("https://account.proton.me/login");

    WebDriverWait wait =
        new WebDriverWait(driver, Duration.ofSeconds(20));

    // Wait for login page URL
    wait.until(
        ExpectedConditions.urlContains("account.proton.me")
    );

    System.out.println("Login Page Title: " + driver.getTitle());
    System.out.println("Login Page URL: " + driver.getCurrentUrl());

    Assert.assertTrue(
        driver.getCurrentUrl().contains("account.proton.me"),
        "Login page did not open"
    );
}

	    
	    @DataProvider(name = "loginData")
	    public Object[][] getData() {
	        return new Object[][] {
	            
	            {"lekhana@proton.me", ""},
	            
	        };
	    }

	    @Test(priority = 3, dataProvider = "loginData" )
	    public void TC3_SignInValidation(String email, String password) throws InterruptedException {

	        // Open login page for every test case
	        driver.get("https://account.proton.me/login");

	        loginpage = new LoginPage(driver);

	        // Perform login using the supplied test data
	        loginpage.login(email, password);
	        
	        if (email.isEmpty() || password.isEmpty()) {

	            // Empty fields should not allow successful login
	            Assert.assertTrue(
	                !driver.getCurrentUrl().contains("/mail/"),
	                "Login should be blocked when a field is empty"
	            );

	        } else {

	            // Wrong credentials should display an error
	            Assert.assertTrue(
	                loginpage.isErrorDisplayed(),
	                "Expected an error message for invalid credentials"
	            );
	        }
	    }
	    
	    
	    
	    @Test(priority = 4)
	    public void TC4_NavigatingToInbox() throws InterruptedException {
	        landingpage.GoTo();   // open login page first
	        inboxpage.GotoInboxpage("Lekhanab5@proton.me", "Lekhana@2002");
	    }
	    
	    
	    
	    @Test(priority = 26, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC6_SignOutFromProtonMail() {
	    	
	    	inboxpage.closesettingswindow();
	        inboxpage.signout();
	        System.out.println("SignOut is successfully completed");
	    }
	    
	    @Test(priority = 7, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC7_ComposeAndSendMail() {
	        inboxpage.composeAndSend("lekhanab5@proton.me","cc_address@example.com",   // CC
	                "bcc_address@example.com", "Test subject", "Hello from Selenium");
	        Assert.assertTrue(inboxpage.isMailSent(), "Mail was not sent");
	    }
	    
	    @Test(priority = 8, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC8_DraftAutoSave() throws InterruptedException {
	        String subject = "AutoSave_" + System.currentTimeMillis();

	        draftspage.createDraft("lekhanab5@proton.me", subject, "Auto save body");
	        draftspage.closeComposer();

	        draftspage.openDrafts();
	        Assert.assertTrue(draftspage.isMailPresent(subject), "Draft not found");
	    }

	    @Test(priority = 9, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC9_DraftAfterRefresh() throws InterruptedException {
	        String subject = "Refresh_" + System.currentTimeMillis();

	        draftspage.createDraft("lekhanab5@proton.me", subject, "Refresh body");
	        driver.navigate().refresh();

	        draftspage.openDrafts();
	        Assert.assertTrue(draftspage.isMailPresent(subject), "Draft lost after refresh");
	    }
	    
	    @Test(priority = 10, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC10_ReopenAndEditDraft() throws InterruptedException {
	        String subject = "Edit_" + System.currentTimeMillis();

	        draftspage.createDraft("lekhanab5@proton.me", subject, "Original text");
	        draftspage.closeComposer();

	        draftspage.openDrafts();
	        draftspage.openDraft(subject);
	        draftspage.addToBody(" EDITED");
	        draftspage.closeComposer();

	        Thread.sleep(3000);
	        driver.navigate().refresh();
	        Thread.sleep(3000);
	        draftspage.openDrafts();

	        Assert.assertTrue(draftspage.isMailPresent(subject), "Draft not found after edit");

	        draftspage.openDraft(subject);
	        Assert.assertTrue(draftspage.getBodyText().contains("EDITED"), "Edit was not saved");
	        
	        Thread.sleep(2000);
	        
	    }
	    
	    @Test(priority = 11, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC11_DiscardDraft() throws InterruptedException {
	        String subject = "Discard_" + System.currentTimeMillis();
            Thread.sleep(3000);
	        draftspage.createDraft("lekhanab5@proton.me", subject, "Delete me");
	        Thread.sleep(3000);
	        draftspage.discardDraft();          // discard from the open composer

	        draftspage.openDrafts();
	        Assert.assertTrue(draftspage.isMailGone(subject), "Draft still exists");
	    }
	    
	    @Test(priority = 12, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC12_DraftToSent() throws InterruptedException {
	        String subject = "DraftToSent_" + System.currentTimeMillis();

	        draftspage.createDraft("lekhanab5@proton.me", subject, "Will be sent");
	        draftspage.closeComposer();

	        draftspage.openDrafts();
	        draftspage.openDraft(subject);
	        draftspage.sendDraft();

	        draftspage.openDrafts();
	        Assert.assertTrue(draftspage.isMailGone(subject), "Draft still in Drafts");

	        draftspage.openSent();
	        Assert.assertTrue(draftspage.isMailPresent(subject), "Mail not found in Sent");
	    }
	    
	 // Puts a new mail in your Inbox
	    private String sendMailToSelf(String prefix) throws InterruptedException {
	        String subject = prefix + "_" + System.currentTimeMillis();
	        draftspage.createDraft("lekhanab5@proton.me", subject, "Test body");
	        draftspage.sendDraft();
	        Thread.sleep(5000);
	        indexandmessageactionpage.openInbox();
	        Assert.assertTrue(indexandmessageactionpage.waitForMail(subject), "Mail did not arrive in Inbox");
	        return subject;
	    }

	    @Test(priority = 13, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC13_OpenAndMarkUnread() throws InterruptedException {
	        String subject = sendMailToSelf("Unread");
	        indexandmessageactionpage.openMessage(subject);
	        indexandmessageactionpage.markUnread();
	        indexandmessageactionpage.openInbox();
	        System.out.println("ROW CLASS: " + indexandmessageactionpage.getRowClass(subject));
	        Assert.assertTrue(indexandmessageactionpage.isUnread(subject), "Not marked unread");
	    }

	    @Test(priority = 14, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC14_StarMessage() throws InterruptedException {
	        String subject = sendMailToSelf("Star");
	        indexandmessageactionpage.star(subject);
	        indexandmessageactionpage.openStarred();
	        Assert.assertTrue(indexandmessageactionpage.isMailPresent(subject), "Not in Starred");
	    }

	    @Test(priority = 16, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC16_TrashAndRestore() throws InterruptedException {
	        String subject = sendMailToSelf("Trash");
	        indexandmessageactionpage.openMessage(subject);
	        indexandmessageactionpage.moveToTrash();
	        indexandmessageactionpage.openInbox();
	        Assert.assertTrue(indexandmessageactionpage.isMailGone(subject), "Still in Inbox");

	        indexandmessageactionpage.openTrash();
	        Assert.assertTrue(indexandmessageactionpage.isMailPresent(subject), "Not in Trash");

	        indexandmessageactionpage.openMessage(subject);
	        indexandmessageactionpage.restore();
	        indexandmessageactionpage.openInbox();
	        Assert.assertTrue(indexandmessageactionpage.waitForMail(subject), "Not restored to Inbox");
	    }

	    @Test(priority = 17, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC17_BulkTrash() throws InterruptedException {
	        String subject1 = sendMailToSelf("Bulk1");
	        String subject2 = sendMailToSelf("Bulk2");

	        indexandmessageactionpage.openInbox();
	        indexandmessageactionpage.selectMessage(subject1);
	        indexandmessageactionpage.selectMessage(subject2);
	        indexandmessageactionpage.moveToTrash();

	        Assert.assertTrue(indexandmessageactionpage.isMailGone(subject1), "Mail 1 still in Inbox");
	        Assert.assertTrue(indexandmessageactionpage.isMailGone(subject2), "Mail 2 still in Inbox");

	        indexandmessageactionpage.openTrash();
	        Assert.assertTrue(indexandmessageactionpage.isMailPresent(subject1), "Mail 1 not in Trash");
	        Assert.assertTrue(indexandmessageactionpage.isMailPresent(subject2), "Mail 2 not in Trash");
	    }
	    
	    @Test(priority = 18, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC18_SearchByKeyword() throws InterruptedException {
	        String subject = sendMailToSelf("Search");   // mail is now in Inbox
	        searchpage.searchMail(subject);
	        Thread.sleep(4000);                          // wait for results
	        Assert.assertTrue(searchpage.isMailPresent(subject), "Mail not found by keyword");
	    }


	    @Test(priority = 19, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC19_SearchNoResult() throws InterruptedException {
	        System.out.println("TC19 STARTED");
	        searchpage.searchMail("zzzNoSuchMail");
	        System.out.println("Rows after search: " + searchpage.mailCount());
	        Thread.sleep(4000);
	        Assert.assertEquals(searchpage.mailCount(), 0, "Mails shown for a keyword that matches nothing");
	    }

	    @Test(priority = 20, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC20_GoToFliter() {
	        filterpage.GoToFilter();
	    }

	    @Test(priority = 21, dependsOnMethods = {"TC20_GoToFliter"})
	    public void TC21_CreatingFliter() throws InterruptedException {
	        filterpage.createFilter("Automation QA", "FilterTest");
	    }

	    @Test(priority = 22, dependsOnMethods = {"TC21_CreatingFliter"})
	    public void TC22_deleteFliter() throws InterruptedException {
	        filterpage.deleteFilterByName("Automation QA");
	        
	    }
	    
	    @Test(priority = 23, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC23_Attachment_Functionality() throws InterruptedException {
            draftspage.ensureInboxIsOpen();
	        String subject = "Attachment Test";
	        String body = "Testing attachment functionality.";

	        String filePath =
	            "C:\\Users\\LENOVO\\Downloads\\QA_Resume  .pdf";

	        String fileName = "testfile.pdf";

	        // Step 1: Create a draft
	        draftspage.createDraft(
	            "your-test-recipient@example.com",
	            subject,
	            body
	        );

	        // Step 2: Upload attachment
	        draftspage.uploadAttachment(filePath);

	        // Step 3: Verify attachment
	        Thread.sleep(3000);
	        Assert.assertTrue(
	            draftspage.verifyAttachmentUploaded(),
	            "FAIL: Attachment was not displayed"
	        );

	        System.out.println("PASS: Attachment uploaded successfully");

	        // Step 4: Wait for draft autosave
	        Thread.sleep(3000);

	        // Step 5: Close composer and open Drafts
	        draftspage.closeComposer();
	        draftspage.openDrafts();

	        // Step 6: Verify draft is present
	        Assert.assertTrue(
	            draftspage.isMailPresent(subject),
	            "FAIL: Draft was not saved"
	        );

	        // Step 7: Reopen the draft
	        draftspage.openDraft(subject);
	        
             Thread.sleep(3000);

	        // Step 8: Verify attachment is still present
	        Assert.assertTrue(
	            draftspage.verifyAttachmentUploaded(),
	            "FAIL: Attachment was not saved in the draft"
	        );

	        System.out.println("PASS: Attachment persisted in the draft");
	    }
	    
	    @Test(priority = 24, dependsOnMethods = {"TC4_NavigatingToInbox"})
	    public void TC24_UndoSend_Functionality() throws InterruptedException {
	    	
	    	draftspage.ensureInboxIsOpen();

	        draftspage.createDraft(
	            "your-test-recipient@example.com",
	            "Undo Send Test",
	            "Testing Undo Send functionality"
	        );

	        draftspage.sendDraft();

	        draftspage.clickUndo();

	        Assert.assertTrue(
	            draftspage.verifyComposerOpen(),
	            "Email did not return to the composer after Undo"
	        );

	        System.out.println("PASS: Undo Send verified");
	    }
	    
	    @AfterClass(alwaysRun = true)
	    public void teardown() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	}