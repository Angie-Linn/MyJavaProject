import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthorizationPage {
   private String urlPage = "http://172.24.120.5:8081/login";
   private By loginTextField = By.id("login-input");
   private By passwordTextField = By.id("password-input");
   private By loginButton = By.id("form_auth_button");
   private By registerButton = By.id("form_register_button");
   private By registerForm = By.xpath("/html/body/div[3]/div/div/div/form/div[2]");
   private WebDriver driver;

    public AuthorizationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToAuthorizationPage(){
        driver.get(urlPage);
    }

    public void fillInLogin (String loginText){
        driver.findElement(loginTextField).sendKeys(loginText);
    }

    public void fillInPassword (String passwordText) {
        driver.findElement(passwordTextField).sendKeys(passwordText);
    }

    public void clickLoginButton () {
        driver.findElement(loginButton).click();
    }

    public void setRegisterButton () {
        driver.findElement(registerButton).click();
    }

    public Boolean loginButtonIsDisplayed(){
        if (driver.findElement(loginButton).isDisplayed()){
            return true;
        } else {
            return false;
        }
    }

    public Boolean registerFormIsDisplayed(){
        if (driver.findElement(registerForm).isDisplayed()){
            return true;
        } else {
            return false;
        }
    }

}
