package com.pom.qa.testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pom.qa.testBase.BaseTest;
import com.pom.qa.testBase.RetryOnTestFail;

public class LoginErrorValidationTest extends BaseTest {
//	LandingPage landingPage;
	@Test(retryAnalyzer = RetryOnTestFail.class)
	public void loginWithWrongCredentialsTest() throws IOException {
		landingPage.login("manishkagathi@gmail.com", "Manish%123");
		String actualErrorMsg = landingPage.getLoginErrorMessage();
		System.out.println(actualErrorMsg);
		Assert.assertEquals(actualErrorMsg, "Incorrect email or password.");
	}
}