package listeners;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import driver.AppDriver;

import extentReport.ExtentManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import pageObject.GetStartedScreen;

import java.util.Objects;

import static extentReport.ExtentTestManager.getTest;
import static extentReport.ExtentTestManager.startTest;


/**
 * Explanation
 * listeners.Listener class provides a set of interfaces that can be used during the test execution
 */


public class TestListeners implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListeners.class.getName());

    @Override
    public void onTestStart(ITestResult result) {
        startTest(result.getMethod().getMethodName(), "");
        GetStartedScreen registrationScreen = new GetStartedScreen();
        registrationScreen.waitForAppLoads(30); // it might take longer time.
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        result.getStatus();//
        getTest().log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED ", ExtentColor.GREEN));

    }

    @Override
    public void onTestFailure(ITestResult result) {
        result.getStatus();
        getTest().log(Status.FAIL, MarkupHelper.createLabel(result.getName() + "FAILED", ExtentColor.RED));
        getTest().fail(result.getThrowable());
        /**
         * handling exception
         */
        try {
            String base64Screenshot =
                    "data:image/png;base64," + ((TakesScreenshot) Objects.requireNonNull(AppDriver.getCurrentDriver())).getScreenshotAs(OutputType.BASE64);
            getTest().log(Status.FAIL, "Test Failed",
                    getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
        } catch (Exception e) {
            e.fillInStackTrace();
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        getTest().log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIPPED ", ExtentColor.YELLOW));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("{} is started", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.extentReports.flush();
    }
}