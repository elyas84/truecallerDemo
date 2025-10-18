package pageObject;

import base.ActionHelper;
import driver.AppDriver;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;

public class VerifyNumberScreen extends BaseScreen {
    protected static final Logger logger = LogManager.getLogger(VerifyNumberScreen.class);
    private By countrySelect;
    private By countryOptionBody;
    private By selectCountyInputPlaceholder;
    private By searchBtn;
    private By phoneNumberScreenTitle;
    private By phoneNumberInputField;
    private By phoneNumberConfirmationModal;
    private By phoneNumberConfirmationModal_phoneNumber;
    private By phoneNumberConfirmationModal_editBtn;
    private By phoneNumberConfirmationModal_Yes_btn;
    private By verifyMyNumberBtn;
    private By verifyMessage;
    private By verifyMessage_OK;

    public VerifyNumberScreen() {
        if (AppDriver.getCurrentDriver() instanceof AndroidDriver) {
            phoneNumberScreenTitle = AppiumBy.id("titleText");
            countrySelect = AppiumBy.id("countryText");
            countryOptionBody = AppiumBy.id("countriesRecyclerView");
            selectCountyInputPlaceholder = AppiumBy.id("titleText");
            searchBtn = AppiumBy.accessibilityId("Search");
            phoneNumberInputField = AppiumBy.id("phoneNumberEditText");
            phoneNumberConfirmationModal = AppiumBy.id("title");
            phoneNumberConfirmationModal_phoneNumber = AppiumBy.id("phoneNumber");
            phoneNumberConfirmationModal_editBtn = AppiumBy.xpath("//android.widget.Button[@text='EDIT']");
            phoneNumberConfirmationModal_Yes_btn = AppiumBy.xpath("//android.widget.Button[@text='YES']");
            verifyMyNumberBtn = AppiumBy.id("nextButton");
            verifyMessage = AppiumBy.id("android:id/message");
            verifyMessage_OK = AppiumBy.id("android:id/button1");
        } else if (AppDriver.getCurrentDriver() instanceof IOSDriver) {
            //
        }
    }

    /**
     * Verify My Number Screen Visibility
     */
    public void verifyMyNumberScreenVisibility() {
        logger.info("[ Verify My Number Screen Visibility ]");
        ActionHelper.waitForElementVisibility(phoneNumberScreenTitle, DEFAULT_TIMEOUT);
        ActionHelper.verifyElementDisplayed(phoneNumberScreenTitle);
        ActionHelper.verifyElementDisplayed(countrySelect);
        ActionHelper.verifyElementDisplayed(selectCountyInputPlaceholder);
    }

    /**
     * Select Country And Register
     */
    public void selectCountryAndRegister(String country, String firstAttemptPhone, String secondAttemptPhone) {
        logger.info("[ Select Country And Register ]");
        click(countrySelect);
        ActionHelper.waitForElementVisibility(selectCountyInputPlaceholder, DEFAULT_TIMEOUT);
        ActionHelper.waitForElementVisibility(searchBtn, DEFAULT_TIMEOUT);
        ActionHelper.waitForElementVisibility(countryOptionBody, DEFAULT_TIMEOUT);
        int attempt = 0;
        while (attempt < 100) {
            boolean flag = false;
            try {
                AppDriver.getCurrentDriver().findElement(By.xpath("//android.widget.TextView[contains(@text,'" + country + "')]")).click();
                flag = true;
            } catch (Exception e) {
                ActionHelper.swipe(ActionHelper.SwipeDirection.UP, 150); //the duration of the scrolling is completely customisable
                attempt++;
                logger.info("swiped, [{}] ", attempt);
            }
            if (flag) {
                break;
            }
        }

        clear(phoneNumberInputField);
        ActionHelper.waitForInSecond(1);
        sendText(phoneNumberInputField, firstAttemptPhone);
        ActionHelper.waitForElementVisibility(verifyMyNumberBtn, DEFAULT_TIMEOUT);
        /**
         *  the first attempt
         */
        click(verifyMyNumberBtn);
        ActionHelper.waitForElementVisibility(phoneNumberConfirmationModal, DEFAULT_TIMEOUT);
        ActionHelper.verifyElementDisplayed(phoneNumberConfirmationModal_phoneNumber);
        ActionHelper.verifyElementDisplayed(phoneNumberConfirmationModal_editBtn);
        ActionHelper.verifyElementDisplayed(phoneNumberConfirmationModal_Yes_btn);
        verifyEditPhoneNumberFeat(firstAttemptPhone);
        /**
         *  the second attempt
         */
        click(verifyMyNumberBtn);
        ActionHelper.waitForElementVisibility(phoneNumberConfirmationModal, DEFAULT_TIMEOUT);
        ActionHelper.verifyElementDisplayed(phoneNumberConfirmationModal_phoneNumber);
        ActionHelper.verifyElementDisplayed(phoneNumberConfirmationModal_editBtn);
        ActionHelper.verifyElementDisplayed(phoneNumberConfirmationModal_Yes_btn);
        verifyEditPhoneNumberFeat(secondAttemptPhone);
    }

    /**
     * very edit number feature
     */

    public void verifyEditPhoneNumberFeat(String phoneNumber) {
        logger.info("[ very edit number feature ]");
        click(phoneNumberConfirmationModal_editBtn);
        sendText(phoneNumberInputField, phoneNumber);
        ActionHelper.waitForElementVisibility(verifyMyNumberBtn, DEFAULT_TIMEOUT);
        click(verifyMyNumberBtn);
        ActionHelper.waitForElementVisibility(phoneNumberConfirmationModal, DEFAULT_TIMEOUT);
        ActionHelper.verifyElementDisplayed(phoneNumberConfirmationModal_phoneNumber);
        ActionHelper.verifyElementDisplayed(phoneNumberConfirmationModal_editBtn);
        ActionHelper.verifyElementDisplayed(phoneNumberConfirmationModal_Yes_btn);
        String numberWithoutSpace = ActionHelper.getAttr(phoneNumberConfirmationModal_phoneNumber, "text").replace(" ", ""); //+46123456789
        Assert.assertTrue(numberWithoutSpace.contains(phoneNumber),
                "Inserted number [" + ActionHelper.getAttr(phoneNumberConfirmationModal_phoneNumber, "text") + "]  and updated [" + phoneNumber + "] are not same");
        click(phoneNumberConfirmationModal_Yes_btn);
        ActionHelper.waitForElementVisibility(verifyMessage, DEFAULT_TIMEOUT);
        click(verifyMessage_OK);
    }

}

