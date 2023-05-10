package eu.strypes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import eu.strypes.helpers.BrowserHelper;

import java.time.Duration;

public class ChatHandler {
    WebDriver driver;

    @FindBy(id="hubspot-conversations-iframe")
    WebElement chatFrame;

    public ChatHandler(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void closeChat() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(chatFrame));

        driver.switchTo().frame(chatFrame);

        WebElement closeButton = null;
        String buttonSelector = "div.widget > div > div > span:nth-child(2) > div > button";
        boolean isDisplayed = false;

        for(int i = 0; i < 10; i++) {
            if(driver.findElements(By.cssSelector(buttonSelector)).size() > 0) {
               closeButton = driver.findElement(By.cssSelector(buttonSelector));
               isDisplayed = true;
               break;
            }
            else {
                BrowserHelper.sleep(1);
            }
        }
        closeButton.click();

        driver.switchTo().defaultContent();
    }
}
/*
    boolean doesExist=false;
        for(int i=0;i<10;i++){
        if(driver.findElements(By.cssSelector("div.widget > div > div > span:nth-child(2) > div > button")).size()>0){
            doesExist=true;
            break;
        }else{
            Thread.sleep(1000);
        }
    }
        Assert.assertTrue("Element does not exist", doesExist);

        (driver.findElement(By.cssSelector("div.widget > div > div > span:nth-child(2) > div > button"))).click();
*/