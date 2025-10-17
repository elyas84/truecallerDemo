package base;

import com.google.common.collect.ImmutableList;
import driver.AppDriver;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

/**
 * Explanation
 * The actionHelper class contains some useful and reusable methods
 * like waiting, clicking, swiping, sending text and more
 * those methods can be used in Base Screen as well based on the design architecture
 */

public class ActionHelper {
    private static final Logger logger = LogManager.getLogger(ActionHelper.class.getName());
    static WebDriverWait wait;
    static AppiumDriver driver;

    /**
     * Implicit wait
     * reason: Sometimes in some cases we also need
     * to use this type of waiting.
     */
    public static void waitForInSecond(int timeout) {
        logger.info("waiting for [{}] second(s)", timeout);
        try {
            Thread.sleep(timeout * 1000L);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    /**
     * Explicit wait --> wait for until some conditions fulfill
     */
    public static void waitForElementVisibility(By locatedBy, int timeout) {
        logger.info("Wait for element to be visible with timeout , [ {} ] second(s)", timeout);
        wait = new WebDriverWait(AppDriver.getCurrentDriver(), Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.presenceOfElementLocated(locatedBy));
    }

    /**
     * Checking and validating the element
     * in this case I did not return a boolean value
     * just asserting the element inside.
     */
    public static void verifyElementDisplayed(By locatedBy) {
        logger.info("Verify element visibility");
        Assert.assertTrue(AppDriver.getCurrentDriver().findElement(locatedBy).isDisplayed(), "[Element is not displayed]");
    }

    public static String getAttr(By locatedBy, String attrName) {
        logger.info("Getting attr value of element, [ {} ], attr name, [ {} ]", locatedBy, attrName);
        return AppDriver.getCurrentDriver().findElement(locatedBy).getAttribute(attrName);
    }


    /**
     * getting the point of screen
     */
    public enum SwipeDirection {
        UP, DOWN, LEFT, RIGHT
    }

    public static void swipe(SwipeDirection direction, int durationOfMillis) {
        logger.info("swiping action will be performing by direction [{}], [{}] millis", direction, durationOfMillis);
        Dimension size = AppDriver.getCurrentDriver().manage().window().getSize();
        System.out.println(size);
        Point middlePoint = new Point((int) (size.width * 0.5), (int) (size.height * 0.5));
        int top = (int) (middlePoint.getY());
        int bottom = (int) (middlePoint.y + (middlePoint.y * 0.5));
        int left = (int) (middlePoint.x - (middlePoint.x * 0.5));
        int right = (int) (middlePoint.x + (middlePoint.x * 0.5));
        switch (direction) {
            case UP ->
                    scroll(new Point(middlePoint.x, bottom), new Point(middlePoint.x, top), Duration.ofMillis(durationOfMillis));
            case DOWN ->
                    scroll(new Point(middlePoint.x, top), new Point(middlePoint.x, bottom), Duration.ofMillis(durationOfMillis));
            case LEFT ->
                    scroll(new Point(right, middlePoint.y), new Point(left, middlePoint.y), Duration.ofMillis(durationOfMillis));
            case RIGHT ->
                    scroll(new Point(left, middlePoint.y), new Point(right, middlePoint.y), Duration.ofMillis(durationOfMillis));
        }
    }

    /**
     * swiping
     */


    public static void scroll(Point start, Point end, Duration duration) {
        logger.info("Start [{}],  End [{}] Duration [{}]", start, end, duration);
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(input, 0).
                addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y))
                .addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg())).
                addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y))
                .addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver = AppDriver.getCurrentDriver();
        driver.perform(ImmutableList.of(swipe));
    }


    /**
     * perform swipe action until an element in view
     */

    public static void scrollDownTillVertically(By locatedBy) {
        logger.info("swiping the screen until element {} into view", locatedBy);
        boolean flag = false;
        int attempt = 0;
        while (attempt < 15) {
            try {
                AppDriver.getCurrentDriver().findElement(locatedBy);
                Dimension size = AppDriver.getCurrentDriver().manage().window().getSize();
                Point middlePoint = new Point((int) (size.width * 0.5), (int) (size.height * 0.5));
                int elementYPosition = AppDriver.getCurrentDriver().findElement(locatedBy).getLocation().getY();
                Dimension elementSize = AppDriver.getCurrentDriver().findElement(locatedBy).getSize();
                int startX = elementSize.getWidth() / 2;
                int startY = elementSize.getHeight() / 2;
                int endY = middlePoint.y;
                if (middlePoint.getY() > elementYPosition) {
                    scroll(new Point(middlePoint.x, elementYPosition), new Point(middlePoint.x, endY + elementYPosition), Duration.ofMillis(800));
                } else if (middlePoint.getY() < elementYPosition) {
                    scroll(new Point(startX, startY), new Point(startX, endY - elementYPosition), Duration.ofMillis(800));
                }
                flag = true;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                swipe(SwipeDirection.UP, 500);
                attempt++;
            }
            if (flag) {
                break;
            }
        }
    }


}
