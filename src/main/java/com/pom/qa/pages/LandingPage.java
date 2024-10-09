package com.pom.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pom.qa.abstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {

	// ---------------- Define All Global Elements -------------------

	WebDriver driver;

	// ----------------- Initializing the page object------------------
	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "userEmail")
	private WebElement emailField;

	@FindBy(id = "userPassword")
	private WebElement pwField;

	@FindBy(id = "login")
	private WebElement loginBtn;

	@FindBy(css = "[class*='flyInOut'")
	private WebElement loginErrorPopup;

	// -------------------All Actions ----------------------

	public void navigateTo(String url) {
		driver.get(url);
	}

	public ProductCataloguePage login(String email, String passWord) {
		emailField.sendKeys(email);
		pwField.sendKeys(passWord);
		loginBtn.click();
		return new ProductCataloguePage(driver);
	}

	public String getLoginErrorMessage() {
		explicitlyWaitUntilVisibilOfWebEle(loginErrorPopup, 5);
		return loginErrorPopup.getText();
	}
}
