package eu.strypes.cucumber;

import org.openqa.selenium.WebDriver;
import eu.strypes.helpers.BrowserHelper;

import java.util.Map;
import java.util.HashMap;

public class StrypesContext {
    private WebDriver driver;
    private final Map<String, String> urlMap = new HashMap<>();

    public StrypesContext() {
        urlMap.put("home", "https://strypes.eu");
    }

    public void setBrowser(String brName, boolean headless) {
        driver = BrowserHelper.newBrowser(brName, headless);
    }

    public WebDriver getBrowser() {
        return driver;
    }

    public void quitBrowser() {
        BrowserHelper.quitBrowser(driver);
    }

    public void goToPage(String name){
        driver.get(getUrl(name));
    }

    public String getUrl(String key) {
        key = key.toLowerCase();
        return urlMap.get(key);
    }
}