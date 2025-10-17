package extentReport;

import base.AppData;
import base.ConfigReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


/**
 * Explanation
 * this package allows to map the test result once test execution completed.
 * regardless of the result, an HTML file will be created at the end of the run.
 * Note -- > we can optimize keys for the test result
 */

public class ExtentManager {
    public static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports createExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/html-reports/report.html");
        reporter.config().setTimeStampFormat("[yyyy-MM-dd] HH:mm:ss");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Platform", AppData.platform);
        if (AppData.platform.equalsIgnoreCase("android")) {
            extentReports.setSystemInfo("App package", ConfigReader.get("appPackage"));
            extentReports.setSystemInfo("App activity", ConfigReader.get("appActivity"));
        } else if (AppData.platform.equalsIgnoreCase("ios")) {
            extentReports.setSystemInfo("App", ConfigReader.get("app"));
        }
        extentReports.setSystemInfo("Device", ConfigReader.get("deviceName"));
        extentReports.setSystemInfo("Udid", ConfigReader.get("udid"));
        extentReports.setSystemInfo("QA", "TrueCaller Automation team");
        extentReports.setSystemInfo("Environment", AppData.env);
        return extentReports;
    }
}