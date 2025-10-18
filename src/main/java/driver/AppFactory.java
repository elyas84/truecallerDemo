
package driver;

import base.ActionHelper;
import base.AppData;
import base.ConfigReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.Duration;

import static driver.AppiumServer.PORT;

/**
 * Explanation
 * The App Factory class is the crucial class that determines
 * what platform will be used for test executions.
 * Key point --> Determination will be done in a form av VM option
 * that can be configured on the EditConfiguration or CLI
 */

public class AppFactory {
    private static final Logger logger = LogManager.getLogger(AppFactory.class.getName());
    static AndroidDriver androidDriver;
    static IOSDriver iosDriver;
    /**
     * Extremely useful for overwriting the default timeout of the Appium server.
     */
    static int timeoutMax = 30000;

    private static void android_appLauncher() {
        logger.info("[ Android app launcher started. App is initiating... ]");
        try {
            /**
             * Note!
             * This is only for testing purpose, and any type of emulators are not
             * recommended for real time testing.
             */
            if (System.getProperty("config").equalsIgnoreCase("app.properties")) {
                String command = "/Users/dev/Library/Android/sdk/emulator/emulator -avd TestDevice";
                Runtime.getRuntime().exec(command);
                logger.info("Emulator started. App is initiating...");
                UiAutomator2Options options = new UiAutomator2Options();
                options.setPlatformName(MobilePlatform.ANDROID).setAppPackage(ConfigReader.get("appPackage"))
                        .setAppActivity(ConfigReader.get("appActivity")).setAutoGrantPermissions(true)
                        .setUdid(ConfigReader.get("udid")).setNoReset(false).setFullReset(false)
                        .setNewCommandTimeout(Duration.ofSeconds(timeoutMax))
                        .setPlatformVersion(ConfigReader.get("platformVersion")).setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
                androidDriver = new AndroidDriver(new URL("http://127.0.0.1:" + PORT + "/"), options);
                logger.info(options);
            } else {
                /**
                 * Assuming we have real device
                 */
                UiAutomator2Options options = new UiAutomator2Options();
                options.setPlatformName(MobilePlatform.ANDROID).setAppPackage(ConfigReader.get("appPackage"))
                        .setAppActivity(ConfigReader.get("appActivity"))
                        .setUdid(ConfigReader.get("udid")).
                setNoReset(true).setFullReset(false)
                        .setNewCommandTimeout(Duration.ofSeconds(timeoutMax))
                        .setPlatformVersion(ConfigReader.get("platformVersion")).setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
                androidDriver = new AndroidDriver(new URL("http://127.0.0.1:" + PORT + "/"), options);
                logger.info(options);
            }
            AppDriver.setDriver(androidDriver);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    private static void iOS_appLauncher() {
        logger.info("[ IOS app launcher started. App is initiating...]");
        try {
            /**
             * Assuming we have real device
             */
            AppiumServer.startTheServer();
            XCUITestOptions options = new XCUITestOptions();
            options.setPlatformName(MobilePlatform.IOS)
                    .setPlatformVersion(ConfigReader.get("platformVersion")).setApp(ConfigReader.get("app")).
                    setAutomationName(AutomationName.IOS_XCUI_TEST)
                    .setNewCommandTimeout(Duration.ofSeconds(timeoutMax)).setAutoDismissAlerts(false) // for now
                    .setDeviceName(ConfigReader.get("deviceName")).setUdid(ConfigReader.get("udid"));
            iosDriver = new IOSDriver(new URL("http://127.0.0.1:" + PORT + "/"), options);
            AppDriver.setDriver(iosDriver);
            logger.info(options);
            logger.info(iosDriver);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public static void launchingApp() {
        logger.info("[ PLATFORM NAME [{}] ]", AppData.platform);
        if (AppData.platform.equalsIgnoreCase("android")) {
            AppFactory.android_appLauncher();
        } else if (AppData.platform.equalsIgnoreCase("ios")) {
            AppFactory.iOS_appLauncher();
        } else {
            throw new Error("[" + AppData.platform + "] is not a valid platform");
        }
        logger.info("[ App is launching... ]");
    }

    public static void closingApp() {
        logger.info("[ App is closing... ]");
        ActionHelper.waitForInSecond(3);
        if (AppData.platform.equalsIgnoreCase("android")) {
            androidDriver.terminateApp(ConfigReader.get("appPackage"));
        } else if (AppData.platform.equalsIgnoreCase("ios")) {
            iosDriver.terminateApp(ConfigReader.get("app"));
        }
    }
}
