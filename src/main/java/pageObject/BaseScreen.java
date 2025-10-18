package pageObject;

import base.ActionHelper;
import driver.AppDriver;
import io.appium.java_client.AppiumBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class BaseScreen {
    protected static final Logger logger = LogManager.getLogger(BaseScreen.class);

    public final int DEFAULT_TIMEOUT = 15;
    public By getStartedBtn;

    /**
     * Explanation
     * Base Screen class is a central place for common locators, methods
     * and those methods, locators can be shared among the other class
     * Note -->
     * This is a full scalable, maintainable mobile automation framework that can handle both the android and the ios platforms
     * with same source code. Developed once and be for always.
     */
    public BaseScreen() {
        PageFactory.initElements(AppDriver.getCurrentDriver(), this);
        getStartedBtn = AppiumBy.id("nextButton");
    }

    /**
     * just for simple wait
     */

    public void waitForAppLoads(int timeout) {
        logger.info("[ Waiting for the app to fully load ]");
        ActionHelper.waitForElementVisibility(getStartedBtn, timeout);
    }

    /**
     * Clear text
     */

    public void clear(By locatedBy) {
        logger.info("[ Clear input fields ]");
        ActionHelper.waitForElementVisibility(locatedBy, DEFAULT_TIMEOUT);
        AppDriver.getCurrentDriver().findElement(locatedBy).clear();
    }

    /**
     * @param locatedBy
     * @param text
     */
    public void sendText(By locatedBy, String text) {
        logger.info("[ Sending text to element, [{}] ]", locatedBy);
        ActionHelper.waitForElementVisibility(locatedBy, DEFAULT_TIMEOUT);
        AppDriver.getCurrentDriver().findElement(locatedBy).sendKeys(text);
    }

    /**
     * @param locatedBy
     */
    public void click(By locatedBy) {
        logger.info("[ Clicking on the element, [{}] ]", locatedBy);
        AppDriver.getCurrentDriver().findElement(locatedBy).click();
    }

}
