package driver;

import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Explanation
 * The AppDriver class designed for initiating and creating
 * only one driver during the test execution known as singleton design pattern.
 */

public class AppDriver {
    private static final Logger logger = LogManager.getLogger(AppDriver.class.getName());
    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    private static AppDriver instance = null;

    private AppDriver() {
    }

    public static AppDriver getInstance() {
        logger.info("[ Processing...]");
        if (instance == null) {
            instance = new AppDriver();
        }
        return instance;
    }

    public AppiumDriver getDriver() {
        return driver.get();
    }

    public static AppiumDriver getCurrentDriver() {
        return getInstance().getDriver();
    }

    public static void setDriver(AppiumDriver appiumDriver) {
        driver.set(appiumDriver);
    }
}