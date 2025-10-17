import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    public static AuthorizationPage authorizationPage;
    public static MainPage mainPage;
    public static WebDriver driver;

    @BeforeAll
    public static void setup(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        authorizationPage = new AuthorizationPage(driver);
        mainPage = new MainPage(driver);
    }

    @AfterAll
    public static void tearDown(){
        driver.quit();
    }
}
