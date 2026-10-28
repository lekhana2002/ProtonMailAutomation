package ProtonMailTest.PageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ProtonMailTest.AbstractComponents.AbstarctComponents;

public class FilterPage extends AbstarctComponents{

	WebDriver driver;
    WebDriverWait wait;
    public FilterPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    By SettingIcon=By.xpath("//button[@data-testid='settings-drawer-app-button:settings-icon']");
    By AllSettingsBtn= By.xpath("//a[@data-testid='drawer-quick-settings:all-settings-button']");
    @FindBy (xpath="//span[@title='Filters']")
    WebElement Filter;
    By addFilterBtn  = By.xpath("//button[text()='Add filter']");
    By nameBox = By.id("name");
    By ThesSubjectDropdown= By.xpath("//button[@aria-live='assertive'and @aria-label='The subject']");
    By senderOption= By.xpath("//button[@title='The sender']");
    By ContainsDropdown= By.xpath("//button[@aria-live='assertive'and @aria-label='contains']");
    By BeginsWithOption=By.xpath("//button[@title='begins with']");
    By keywordBox = By.xpath("//input[@placeholder='Type text or keyword']");
    By insertBtn  = By.xpath("//button[text()='Insert']");

   
    @FindBy(id = "move-to-select")
    WebElement moveToDropdown;
    
    By ArchiveOption=By.xpath("//button[@title='Archive']"); 

    @FindBy(xpath = "//button[normalize-space()='Create folder']")
    WebElement createFolderBtn;

    @FindBy(xpath = "//input[@type='checkbox']")
    List<WebElement> checkboxes;

    @FindBy(xpath = "//button[normalize-space()='Preview']")
    WebElement previewBtn;
    
    @FindBy(xpath = "//span[text()='Read']")
    WebElement markAsRead;

    // Mark as Starred
    @FindBy(xpath = "//span[text()='Starred']")
    WebElement markAsStarred;
    
    @FindBy(xpath = "//input[@id='applyfilter']")
    WebElement ApplyFilter;

    @FindBy(xpath = "//label[contains(.,'Apply filter to existing emails')]")
    WebElement applyExistingEmails;
    By qaLabel = By.xpath("//label[@title='QA']");


    By starredCheck = By.xpath("//dialog//label[contains(.,'Starred')]");
    By saveBtn      = By.xpath("//dialog//button[normalize-space()='Save']");

    @FindBy (xpath="//button[@data-testid='filter-modal:next-button']")
    WebElement nextBtn;
    By deleteButton = By.xpath( "//li[contains(@class,'dropdown-item--delete')]//button");
    @FindBy(xpath = "//button[text()='Delete']")
    WebElement FinaldeleteButton;

public void GoToFilter() {

    wait.until(
        ExpectedConditions.elementToBeClickable(SettingIcon)
    ).click();

    wait.until(
        ExpectedConditions.elementToBeClickable(AllSettingsBtn)
    ).click();

    By filterLocator = By.xpath(
        "//*[self::a or self::button][contains(normalize-space(.),'Filters')]"
    );

    WebElement filterElement = wait.until(
        ExpectedConditions.elementToBeClickable(filterLocator)
    );

    scrollToElement(filterElement);

    filterElement.click();
    
    
}

public void createFilter(String name, String subjectText) throws InterruptedException {
	wait.until(
            ExpectedConditions.elementToBeClickable(addFilterBtn)
        ).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(nameBox)).sendKeys(name);
    wait.until(ExpectedConditions.elementToBeClickable(nextBtn)).click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(ThesSubjectDropdown)).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(senderOption)).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(ContainsDropdown)).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(BeginsWithOption)).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(keywordBox)).sendKeys(subjectText);
    wait.until(ExpectedConditions.elementToBeClickable(insertBtn)).click();
    wait.until(ExpectedConditions.elementToBeClickable(nextBtn)).click();

    wait.until(ExpectedConditions.elementToBeClickable(qaLabel)).click();

    wait.until(ExpectedConditions.elementToBeClickable(moveToDropdown)).click();
    wait.until(ExpectedConditions.elementToBeClickable(ArchiveOption)).click();
    scrollToElement(ApplyFilter);
    WebElement readCheckbox = driver.findElement(
    	    By.xpath("//input[@type='checkbox' and following-sibling::span[normalize-space()='Read']]")
    	);

    	if (!readCheckbox.isSelected()) {
    	    jsClick(readCheckbox);
    	}
    wait.until(ExpectedConditions.elementToBeClickable(applyExistingEmails)).click();

   
    Thread.sleep(3000);
    wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
   
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//dialog")));
}


public boolean isFilterPresent(String name) {
    List<WebElement> filters = driver.findElements(
        By.xpath("//*[normalize-space(text())='" + name + "']")
    );

    return !filters.isEmpty();
}



public void deleteFilterByName(String name) throws InterruptedException {

 
    By modalOverlay = By.cssSelector("div.modal-two");

    wait.until(
        ExpectedConditions.invisibilityOfElementLocated(modalOverlay)
    );


    By filterName = By.xpath(
        "//*[normalize-space(text())='" + name + "']"
    );

    wait.until(
        ExpectedConditions.visibilityOfElementLocated(filterName)
    );

    By rowDropdown = By.xpath(
        "//*[normalize-space(text())='" + name + "']" +
        "/ancestor::*[.//button[@data-testid='dropdownActions:dropdown']][1]" +
        "//button[@data-testid='dropdownActions:dropdown']"
    );

    wait.until(
        ExpectedConditions.elementToBeClickable(rowDropdown)
    ).click();


    By deleteOption = By.xpath(
        "//button[contains(@aria-label,'Delete filter')]"
    );

    wait.until(
        ExpectedConditions.elementToBeClickable(deleteOption)
    ).click();
    
    Thread.sleep(3000);
 
    jsClick(FinaldeleteButton);

    // Verify that the filter was deleted
    wait.until(
        ExpectedConditions.invisibilityOfElementLocated(filterName)
    );
}



}
