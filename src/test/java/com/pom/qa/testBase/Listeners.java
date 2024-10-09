package com.pom.qa.testBase;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.pom.qa.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {

	ExtentReports extentReports = ExtentReporterNG.getExtentReportObject();
	ExtentTest test; // Holds Entry into ur report
	// If tests are running in parallel, ExtentTest test will be replaced with other
	// @Test
	// To make this ExtentTest as threadSafe, we need to use ThreadLoacl.

	ThreadLocal<ExtentTest> threadSafeTest = new ThreadLocal<ExtentTest>();

	// Before executing any test
	@Override
	public void onTestStart(ITestResult result) {
		// Create Entry for test before starting any test execution
		// Pass Name of the test. to know for which test this is executing
		test = extentReports.createTest(result.getMethod().getMethodName());
		// Once execution is started and extent test has been initiated to one test.
		// that test should be mapped with unique Id.
		threadSafeTest.set(test); // unique thread id(ErrorValidationTest)->test
	}

	// On Success
	@Override
	public void onTestSuccess(ITestResult result) {
		// Log this info if test get passed
		threadSafeTest.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// IF test get failed we should know, why test is failed. for that we need error
		// message. which is stored in result as Throwable.
		// To generate report for correct test, use ThreadLocal instance to get its
		// unique id to log error in report
		threadSafeTest.get().fail(result.getThrowable());

		// Fetch driver from result,
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Need Screenshot and attach it to report
		String filePath = null;
		// Pass TestName so that screen shot will be names on it.
		// Pass driver, As driver is needed to take screenShot
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Pass ScreenShot Path where it is stored in local to attach it to report,
		// Title of the ScreenShot in report
		threadSafeTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// Empty body
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// Empty body
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// Empty body
	}

	@Override
	public void onStart(ITestContext context) {
		// Empty body
	}

	@Override
	public void onFinish(ITestContext context) {
		// Without flushing report wont be generated.Just entries will be created
		// Created entries will be reported after flush();
		extentReports.flush();
	}
}
