package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Explanation
 * Properties class allows us to get and read the data.
 */

public class ConfigReader {
    private static final Logger logger = LogManager.getLogger(ConfigReader.class.getName());
    public static Properties properties;

    static {
        try {
            logger.info("Reading config file, [{}]", System.getProperty("config"));
            String path = AppData.config;
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (Exception e) {
            throw new Error("Failed to read config file - [server is down or device is off]");
        }
    }

    public static String get(String keyName) {
        return properties.getProperty(keyName);
    }
}