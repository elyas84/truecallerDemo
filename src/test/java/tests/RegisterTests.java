package tests;


import base.ConfigReader;
import base.TestBase;
import org.testng.annotations.Test;
import pageObject.GetStartedScreen;
import pageObject.VerifyNumberScreen;

public class RegisterTests extends TestBase {

    GetStartedScreen getStartedScreen;
    VerifyNumberScreen verifyNumberScreen;

    @Test
    void registerNewAccount() {
        getStartedScreen = new GetStartedScreen();
        getStartedScreen.verifyGetStartedScreenVisibility();
        verifyNumberScreen = getStartedScreen.getStartedScreenModalHandler();
        verifyNumberScreen.verifyMyNumberScreenVisibility();
        verifyNumberScreen.selectCountryAndRegister(ConfigReader.get("country"),
                ConfigReader.get("phoneNumber_firstAttempt"), ConfigReader.get("phoneNumber_secondAttempt"));
    }
}
