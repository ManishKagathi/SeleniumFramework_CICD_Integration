package com.pom.qa.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pom.qa.abstractComponents.AbstractComponent;

public class ConfirmPage extends AbstractComponent {

	// ---------------- Define All Global Elements -------------------
	WebDriver driver;

	// ----------------- Initializing the page object-----------------
	public ConfirmPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".hero-primary")
	private WebElement confirmTextElemet;

	@FindBy(css = "label.ng-star-inserted")
	private List<WebElement> orderIdEle;

	// -------------------All Actions ----------------------
	public String getConfirmMsg() {
		String confirmMsg = confirmTextElemet.getText();
		return confirmMsg;
	}

	public List<String> getOrderIds() {
		List<String> orderIds = orderIdEle.stream().map(eachId -> eachId.getText().replace("|", "").trim())
				.collect(Collectors.toList());
		return orderIds;
	}

}
