package pageObject;

import base.ActionHelper;
import driver.AppDriver;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class GetStartedScreen extends BaseScreen {
    protected static final Logger logger = LogManager.getLogger(GetStartedScreen.class);
    private By brandLogoId;
    private By googleOverlayCloseBtnId;
    private By useGoogleAccountCancelBtn;
    private By dataPermissionContinueBtn;
    private By permission_allow_btn;
    private By OkBtn;
    private By getStartedScreenTitle;
    private By getStartedScreenSubTitle;
    private By dontAskMeAgainCheckbox;
    private By setAsDefaultBtn;
    private By permission_msg_title;
    private By makeDefaultSMSAppOverlayTitle;
    private By setDefaultOptionList;

    public GetStartedScreen() {
        /**
         * can be empty contractor!
         */
        PageFactory.initElements(AppDriver.getCurrentDriver(), this);

        if (AppDriver.getCurrentDriver() instanceof AndroidDriver) {
            brandLogoId = AppiumBy.id("wizardLogo");
            getStartedScreenTitle = AppiumBy.id("title");
            getStartedScreenSubTitle = AppiumBy.id("subtitle");
            dontAskMeAgainCheckbox = AppiumBy.id("dont_ask_again");
            setAsDefaultBtn = AppiumBy.id("button1");
            googleOverlayCloseBtnId = AppiumBy.accessibilityId("Cancel");
            useGoogleAccountCancelBtn = AppiumBy.id("cancelButton");
            dataPermissionContinueBtn = AppiumBy.id("button1");
            permission_msg_title = AppiumBy.id("permission_message");
            permission_allow_btn = AppiumBy.id("permission_allow_button");
            makeDefaultSMSAppOverlayTitle = AppiumBy.xpath("//android.widget.TextView[@text='Set Truecaller as your default SMS app?']");
            setDefaultOptionList = AppiumBy.xpath("//android.widget.ListView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView");
            OkBtn = AppiumBy.xpath("//android.widget.Button[@resource-id='android:id/button1']");


        } else if (AppDriver.getCurrentDriver() instanceof IOSDriver) {
            brandLogoId = AppiumBy.accessibilityId("");
            /**
             * assuming we have them here
             */
        }
    }

    public void verifyGetStartedScreenVisibility() {
        ActionHelper.waitForElementVisibility(brandLogoId, DEFAULT_TIMEOUT);
        ActionHelper.verifyElementDisplayed(brandLogoId);
    }


    public VerifyNumberScreen getStartedScreenModalHandler() {
        boolean isHandled = false;
        ActionHelper.waitForElementVisibility(getStartedBtn, DEFAULT_TIMEOUT);
        click(getStartedBtn);
        int attempt = 0;
        while (attempt < 10) {
            try {
                googleSignUpModalHandler();
                isHandled = true;
            } catch (Exception e) {
                ActionHelper.waitForElementVisibility(dataPermissionContinueBtn, DEFAULT_TIMEOUT);
                click(dataPermissionContinueBtn);
                int y = 0;
                while (y < 5) {
                    try {
                        ActionHelper.waitForElementVisibility(permission_allow_btn, 3);
                        click(permission_allow_btn);
                    } catch (Exception el) {
                        y++;
                    }
                }
                attempt++;
            }
            if (isHandled) {
                break;
            }
        }
        return new VerifyNumberScreen();
    }

    /**
     * Google signing handler
     */
    public void googleSignUpModalHandler() {
        logger.info("google signup modal handler");
        ActionHelper.waitForElementVisibility(googleOverlayCloseBtnId, DEFAULT_TIMEOUT);
        click(googleOverlayCloseBtnId);
        ActionHelper.waitForElementVisibility(useGoogleAccountCancelBtn, DEFAULT_TIMEOUT);
        click(useGoogleAccountCancelBtn);
        ActionHelper.waitForElementVisibility(OkBtn, DEFAULT_TIMEOUT);
        click(OkBtn);
    }

}
