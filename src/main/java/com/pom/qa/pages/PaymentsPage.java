package com.pom.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pom.qa.abstractComponents.AbstractComponent;

public class PaymentsPage extends AbstractComponent {

	// ---------------- Define All Global Elements -------------------
	WebDriver driver;

	// ----------------- Initializing the page object------------------
	public PaymentsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "[placeholder='Select Country']")
	private WebElement selectCountryField;

	@FindBy(css = ".ta-item")
	private List<WebElement> countryOptions;

	@FindBy(css = ".action__submit")
	private WebElement placeOrderBtn;

	private By dropDownCantainerByLocater = By.cssSelector(".ta-results");
	private By placeOrderBtnByLocater = By.cssSelector(".action__submit");

	// -------------------All Actions ----------------------
	public void selectCountry(String inputCountry) {
		selectCountryField.sendKeys(inputCountry);
		explicitlyWaitUntilVisibilityOfElementLocated(dropDownCantainerByLocater, 5);
		countryOptions.forEach(count -> System.out.println(count.getText()));

		WebElement myCountry = countryOptions.stream().filter(country -> country.getText().equalsIgnoreCase("INDIA"))
				.findFirst().orElse(null);

		javaScriptExecuterScrollBy(0, 500);

		explicitlyWaitUntilelementToBeClickable(myCountry, 5);

		if (myCountry != null) {
			System.out.println(myCountry.getText());
			myCountry.click();
		}
	}

	public ConfirmPage clickOnPlaceOrder() throws InterruptedException {
		javaScriptExecuterScrollBy(0, 500);
		System.out.println("scrolled");

		explicitlyWaitUntilelementToBeClickable(placeOrderBtn, 5);
		explicitlyWaitUntilVisibilityOfElementLocated(placeOrderBtnByLocater, 5);

		Thread.sleep(3000);
		placeOrderBtn.click();
		return new ConfirmPage(driver);
	}
}
