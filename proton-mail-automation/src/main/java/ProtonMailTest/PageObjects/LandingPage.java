package ProtonMailTest.PageObjects;

import static org.testng.Assert.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ProtonMailTest.AbstractComponents.AbstarctComponents;

public class LandingPage extends AbstarctComponents {

	WebDriver driver;

	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);

	}
	
	By SignInLink=By.linkText("Sign in");
	
	public void GoTo() {
		driver.get("https://proton.me/mail");
		String pagetitle=driver.getTitle();
		assertEquals(pagetitle, "Proton Mail: Get a free email account with privacy and encryption | Proton");
		WebElement element= waitUntilTheElementIsLocated(SignInLink);
		element.click();
		
	}
}

	
