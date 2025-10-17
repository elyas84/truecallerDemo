package base;


/**
 * Explanation
 * The AppData class contains a set of key and value
 * and those keys and values are extremely useful for CI
 * and platform based mobile automation
 * (not necessarily mobile but for other automation and the cli execution as well)
 */

public class AppData {
    public static String platform = System.getProperty("platform", "android");
    public static String env = System.getProperty("env", "release");
    public static String config = System.getProperty("config", "app.properties");
}
