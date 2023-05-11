package eu.strypes.cucumber;

import eu.strypes.pages.SearchPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import eu.strypes.helpers.BrowserHelper;
import eu.strypes.helpers.PageHelper;
import eu.strypes.helpers.UserHelper;
import eu.strypes.pages.HomePage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HomePageSteps {
    private final StrypesContext context;
    private HomePage homePage;

    public HomePageSteps(StrypesContext context) {
        this.context = context;
    }

    // Background
    @Given("user is at home page")
    public void userIsAtHomePage() {
        context.setBrowser("Firefox", false);
        context.goToPage("home");
        homePage = new HomePage(context.getBrowser());
    }

    // Verify search field function with missing search query
    @Given("search form is open")
    public void searchFormIsOpen() {
        homePage.clickOpenSearchButton();
    }

    @When("user enters missing phrase")
    public void userEntersMissingPhrase() {
        String term = "missing_phrase";
        homePage.enterSearchTerm(term);
        PageHelper.pressEnter(context.getBrowser());
    }

    @Then("no results should be found")
    public void noResultsShouldBeFound() {
        SearchPage searchPage = new SearchPage(context.getBrowser());
        assertTrue(searchPage.nothingFoundMessageIsDisplayed(), "Incorrect search results are found");
    }

    // Verify if Main menu links are leading to correct pages
    @Then("verify Main menu links")
    public void verifyMainMenuLinks() {
        // Verify for broken links
        List<String> linkURLs = BrowserHelper.getLinksProperty(homePage.getMainMenuItems(), "url");
        List<String> brokenLinks = new ArrayList<>();

        boolean hasBrokenLinks = false;
        for(String url : linkURLs) {
            if(!BrowserHelper.validateLink(url)) {
                hasBrokenLinks = true;
                brokenLinks.add(url);
            }
        }
        if(hasBrokenLinks) {
            System.out.println("The Main menu links are broken :");
            for(String link : brokenLinks)
                System.out.println("Link with URL : " + link);
        }
        assertFalse(hasBrokenLinks, "Main menu contains items with broken links");

        // Verify if links are leading to correct pages
        List<String> targetURLs = BrowserHelper.getLinksProperty(homePage.getMainMenuItems(), "url");
        List<String> itemNames = BrowserHelper.getLinksProperty(homePage.getMainMenuItems(), "text");
        List<String> badItemsNames = new ArrayList<>();

        String url, name, actualURL, actualName;
        boolean hasBadLinks = false;
        for(int i = 0; i < targetURLs.size(); i++) {

            url = targetURLs.get(i);
            if(url.charAt(url.length() - 1) != '/') url += '/';

            name = itemNames.get(i);
            // The name of this item is too long and can be truncated
            if(name.equals("Remote Diagnostics, Monitoring and Predictive Maintenance")) name = "Remote Diagnostics";

            context.getBrowser().get(url);
            actualURL = context.getBrowser().getCurrentUrl();
            actualName = context.getBrowser().getTitle();

            if(!actualURL.equals(url) || !actualName.contains(name)) {
                badItemsNames.add(itemNames.get(i));
                hasBadLinks = true;
            }
        }

        if(hasBadLinks) {
            System.out.println("The following items are not leading to correct page");
            for (String item : badItemsNames)
                System.out.println("Menu item : " + item);
        }
        assertFalse(hasBadLinks, "There are menu items with incorrect links");
    }

    // Verify if Main menu top level links are changing their color on hover
    @Then("verify Main menu top links hover color")
    public void verifyMainMenuTopLinksHoverColor() {
        List<WebElement> menuItems = homePage.getMainMenuTopItems();
        List<String> badItems = new ArrayList<>();
        Actions action = new Actions(context.getBrowser());

        // Map of expected color change by design
        boolean[] expectedChange = new boolean[] {true, true, true, true, true, false};
        boolean badBehavior = false;
        for(int i = 0; i < 6; i++) {
            String defaultColor = menuItems.get(i).getCssValue("color");
            action.moveToElement(menuItems.get(i)).perform();
            BrowserHelper.sleep(3);
            String hoverColor = menuItems.get(i).getCssValue("color");

            boolean actualChange = !hoverColor.equals(defaultColor);
            if(actualChange != expectedChange[i]) {
                badBehavior = true;
                badItems.add(menuItems.get(i).getText());
            }
        }

        if(badBehavior) {
            System.out.println("Following menu items doesn't change their color on hover as expected by design:");
            for(String item : badItems)
                System.out.println(item);
        }
        assertFalse(badBehavior, "Menu items color on hover doesn't change as expected");
    }

    // Verify if Back To Top button is hidden when user is at the top of the page
    @When("user is at top of the page")
    public void userIsAtTopOfThePage() {
        if(PageHelper.getYScroll(context.getBrowser()) > 0)
            PageHelper.scrollToTop(context.getBrowser());
    }

    @Then("Back To Top button is hidden")
    public void backToTopButtonIsHidden() {
        assertFalse(homePage.backToTopButtonIsVisible(), "Back To Top button is visible when user is at the top of the page");
    }

    // Verify if Back To Top button is displayed when user is not at the top of the page
    @When("user is not at top of the page")
    public void userIsNotAtTopOfThePage() {
        int offset = BrowserHelper.getWindowHeight(context.getBrowser());
        PageHelper.scrollY(context.getBrowser(), offset);
    }

    @Then("Back To Top button is displayed")
    public void backToTopButtonIsDisplayed() {
        assertTrue(homePage.backToTopButtonIsVisible(), "Back To Top button is not displayed when user is not at the top of the page");
    }

    // User attempts newsletter subscription with valid not subscribed email
    @Given("user enters valid not subscribed email")
    public void userEntersValidNotSubscribedEmail() {
        String email = UserHelper.nameToEmail("test_user", true);
        homePage.enterNewsletterEmail(email);
    }

    @And("user checks the Privacy policy checkbox")
    public void userChecksThePrivacyPolicyCheckbox() {
        // Scroll to the bottom of page so newsletter privacy policy checkbox is visible
        PageHelper.scrollToBottom(context.getBrowser());

        homePage.checkNewsletterPrivacyCheckbox();
    }

    @When("user clicks Subscribe button")
    public void userClicksSubscribeButton() {
        homePage.clickNewsletterButton();
    }

    @Then("user is subscribed")
    public void userIsSubscribed() {
        assertTrue(homePage.subscrWelcomeMessageIsDisplayed(), "User is not subscribed");
    }

    @And("correct welcome message is displayed")
    public void correctWelcomeMessageIsDisplayed() {
        String expectedMsg = "Thank you for signing up for our newsletter! You are in!";
        assertEquals(expectedMsg, homePage.subscrWelcomeMessageText(), "Welcome message is not correct");
    }

    // Restore to initial state
    @After
    public void quitBrowser() {
        context.quitBrowser();
    }
}