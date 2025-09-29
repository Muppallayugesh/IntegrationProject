package StepDefinitions;

import Utils.Browsers;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;

public class LoginPageSteps {


    WebDriver driver = Browsers.tlDriver.get();
    public LoginPage loginPage = new LoginPage(driver);
    public LoginPageSteps() {
        this.driver = Browsers.tlDriver.get();
    }

    @Given("User is on Facebook login page")
    public void userIsOnFacebookLoginPage() throws InterruptedException {
        loginPage.driver.get("https://www.facebook.com/");
        Thread.sleep(2000);
        System.out.println("User is on Facebook login page");
    }

    @When("User enters valid username and password")
    public void userEntersValidUsernameAndPassword() {

        System.out.println("User enters valid username and password");
    }

    @And("User clicks on login button")
    public void userClicksOnLoginButton() {
        System.out.println("User clicks on login button");
    }

    @Then("User should be logged in successfully")
    public void userShouldBeLoggedInSuccessfully() {
        System.out.println("User should be logged in successfully");
    }

    @When("User enters invalid username and password")
    public void userEntersInvalidUsernameAndPassword() {
        loginPage.enterUsername("invalid_user");
        loginPage.enterPassword("invalid_pass");
        System.out.println("User enters invalid username and password");
    }

    @Then("User should see an error message")
    public void userShouldSeeAnErrorMessage() {
        System.out.println("User should see an error message");
    }
}
