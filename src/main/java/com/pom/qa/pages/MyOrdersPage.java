package com.pom.qa.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyOrdersPage {
	WebDriver driver;

	public MyOrdersPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "tr th:nth-child(1)")
	private List<WebElement> orderIdHistoryEle;

	public List<String> FetchOrderIdsFromOrderHistory() {
		List<String> orderIdHistory = orderIdHistoryEle.stream().map(order -> order.getText())
				.collect(Collectors.toList());
		return orderIdHistory;
	}
}