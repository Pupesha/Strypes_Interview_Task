package eu.strypes.helpers;

import org.openqa.selenium.*;

public class PageHelper {

    public static Long getYScroll(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Long) js.executeScript("return window.pageYOffset;");
    }

    public static void scrollY(WebDriver driver, double offset) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, " + offset + ")");
    }

    public static void scrollToTop(WebDriver driver) {
        driver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL, Keys.HOME);
    }

    public static void scrollToBottom(WebDriver driver) {
        driver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL, Keys.END);
    }

    public static void jsClick(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public static void pressEnter(WebDriver driver) {
        driver.findElement(By.tagName("body")).sendKeys(Keys.ENTER);
    }
}