package eu.strypes.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import eu.strypes.helpers.PageHelper;

public class HomePage {
    private final WebDriver driver;

    @FindBy(css = "div.elementor-search-form__toggle")
    WebElement openSearchButton;

    @FindBy(css = "input.elementor-search-form__input")
    WebElement searchField;

    @FindBy(xpath = "(//*[@id='menu-1-50af2d3b'])[1]//child::a")
    List<WebElement> mainMenuItems;

    @FindBy(xpath = "(//*[@id='menu-1-50af2d3b'])[1]/li/a")
    List<WebElement> mainMenuTopItems;

    @FindBy(id = "ast-scroll-top")
    WebElement backToTopButton;

    @FindBy(id = "form-field-email")
    WebElement newsletterEmail;

    @FindBy(css = "[name = Newsletter] button")
    WebElement newsletterButton;

    @FindBy(id = "form-field-privacy_policy_checkbox")
    WebElement newsletterPrivacyCheckbox;

    @FindBy(css = "div.elementor-message.elementor-message-success")
    WebElement subscrWelcomeMessage;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOpenSearchButton() {
        openSearchButton.click();
    }

    public void enterSearchTerm(String searchTerm) {
        searchField.sendKeys(searchTerm);
    }

    public boolean backToTopButtonIsVisible() {
        return backToTopButton.isDisplayed();
    }

    public void enterNewsletterEmail(String email) {
        newsletterEmail.sendKeys(email);
    }

    public void checkNewsletterPrivacyCheckbox() {
        if (!newsletterPrivacyCheckbox.isSelected())
            PageHelper.jsClick(driver, newsletterPrivacyCheckbox);
    }

    public void clickNewsletterButton() {
        newsletterButton.click();
    }

    public boolean subscrWelcomeMessageIsDisplayed() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(subscrWelcomeMessage));

        return subscrWelcomeMessage.isDisplayed();
    }

    public String subscrWelcomeMessageText() {
        return subscrWelcomeMessage.getText();
    }

    public List<WebElement> getMainMenuTopItems() {
        return mainMenuTopItems;
    }

    public List<WebElement> getMainMenuItems () {
        return mainMenuItems;
    }

}
