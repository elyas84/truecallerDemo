package base;


import driver.AppFactory;
import driver.AppiumServer;
import listeners.TestListeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

/**
 * The class extension requires same className
 * that we created for the listeners
 * in this class name is TestListeners
 */
@Listeners(TestListeners.class)
public class TestBase {
    private static final Logger logger = LogManager.getLogger(TestBase.class.getName());

    @BeforeTest(alwaysRun = true)
    public static void startAppiumServer() {
        logger.info("[ Before test initiating... ]");
        AppiumServer.startTheServer();
    }

    @AfterTest(alwaysRun = true)
    public void endAppiumServer() {
        logger.info("[ After test initiating... ]");
        ActionHelper.waitForInSecond(3);
        AppiumServer.killTheServer();
    }


    @BeforeMethod(alwaysRun = true)
    public static void setup() {
        logger.info("[ Before method initiating... ]");
        AppFactory.launchingApp();
    }

    @AfterMethod(alwaysRun = true)
    public static void tearDown() {
        logger.info("[ After method initiating... ]");
        AppFactory.closingApp();
    }

}
