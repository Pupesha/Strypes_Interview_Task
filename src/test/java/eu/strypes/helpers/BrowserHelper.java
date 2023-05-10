package eu.strypes.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class BrowserHelper {

    public static WebDriver newBrowser(String brName, boolean headless) {
        WebDriver driver = null;

        brName = brName.toLowerCase();
        switch (brName) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if(headless) chromeOptions.addArguments("--headless");

                System.setProperty("webdriver.chrome.driver", ".drivers/chromedriver.exe");
                driver = new ChromeDriver(chromeOptions);
            break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless");

                driver = new EdgeDriver(edgeOptions);
                System.setProperty("webdriver.edge.driver", ".drivers/edgedriver.exe");
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if(headless) firefoxOptions.addArguments("--headless");

                driver = new FirefoxDriver(firefoxOptions);
                System.setProperty("webdriver.gecko.driver", ".drivers/geckodriver.exe");
            break;
        }
        return driver;
    }

    public static int getWindowWidth(WebDriver driver) {
        return driver.manage().window().getSize().getWidth();
    }

    public static int getWindowHeight(WebDriver driver) {
        return driver.manage().window().getSize().getHeight();
    }

    public static void quitBrowser(WebDriver driver) {
        driver.quit();
    }

    public static void sleep(int seconds) {
        seconds *= 1000;
        try {
            Thread.sleep(seconds);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getLinksProperty(List<WebElement> links, String property) {
        List<String> properties = new ArrayList<>();

        property = property.toLowerCase();
        switch (property) {
            case "url":
                for(WebElement link : links)
                    properties.add(link.getAttribute("href"));
                break;
            case "text":
                for(WebElement link : links)
                    properties.add(link.getAttribute("text"));
                break;
        }
        return properties;
    }

    public static boolean validateLink(String linkUrl) {
        try {
            URL url = new URL(linkUrl);

            // Creating url connection and getting a response code
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();

            // if response code is greater than or equal to 400 means link can't be opened
            if (httpURLConnection.getResponseCode() >= 400)
                return false;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
