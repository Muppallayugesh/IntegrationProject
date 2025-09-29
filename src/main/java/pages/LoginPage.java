package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    // Login page elements and methods
    // This class represents the Login Page of the application
    // It contains the locators and methods to interact with the Login Page elements

    // Web elements
    // These are the locators for the username, password, and login button elements on the Login Page
       public WebDriver driver;
         public LoginPage(WebDriver driver){
              this.driver=driver;
         }
         By username= By.xpath("//input[@id='username']");
         By password=By.xpath("//input[@id='password']");
         By loginButton=By.xpath("//button[@id='login']");
            By logoutButton=By.xpath("//a[@id='logout']");
    // Methods to interact with the Login Page elements
    // These methods perform actions on the Login Page elements, such as entering username, password, and clicking the login button
       public void enterUsername(String user) {
           driver.findElement(username).sendKeys(user);
           System.out.println("username entered");
       }
       public void enterPassword(String pass) {
           driver.findElement(password).sendKeys(pass);
           System.out.println("password entered");
       }
       public void clickLoginButton() {
           driver.findElement(loginButton).click();
           System.out.println("login button clicked");
       }
         public void clickLogoutButton() {
           driver.findElement(logoutButton).click();
           System.out.println("logout button clicked");
         }


}
