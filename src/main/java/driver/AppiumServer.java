package driver;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Explanation
 * Starting the appium server in programmatically is the best approach
 * to go with when it comes to CI or CLI.
 * for locating elements we always use 'Terminal'
 */
public class AppiumServer {

    public static AppiumDriverLocalService server;
    public static int PORT = 4748;
    private static final Logger logger = LogManager.getLogger(AppiumServer.class.getName());

    public static void setAppiumInstance() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withAppiumJS(new File("/usr/local/lib/node_modules/appium/index.js")).
                usingDriverExecutable(new File("/usr/local/bin/node")).usingPort(PORT).withIPAddress("127.0.0.1");
        server = AppiumDriverLocalService.buildService(builder);
    }

    static AppiumDriverLocalService getAppiumInstance() {
        if (server == null) {
            setAppiumInstance();
        }
        return server;
    }

    public static void startTheServer() {
        logger.info("[ Appium server is starting on PORT [{}] ]", PORT);
        getAppiumInstance().start();

    }


    public static void killTheServer() {
        if (server != null) {
            getAppiumInstance().stop();
            logger.info("[ Appium server is closed....]");
        }
    }

}