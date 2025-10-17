import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class MyAllureSteps {
    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @AfterEach
    public void afterTest(){makeScreenshot(driver);}



    @Attachment(value = "Вложение", type = "application/json", fileExtension = ".txt")
    public static byte[] getAttachmentWithArgs(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get("src/test/resources", resourceName));
    }

    @Attachment(value = "Attachment Screenshot", type = "image/png")
    public byte[] makeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static void addText() {
        String text = "Текст отчета по завершению теста";
        Allure.addAttachment("Результат", "text/plain", text);
    }


    @Step("Шаг 1: Выполнение действия")
    public void performAction(Allure.ThrowableRunnableVoid action, String stepDescription) {
        Allure.step(stepDescription, action);
    }

    @Step
    public void setLoginAndPass(){
        try {
            // Код для выполнения теста
            driver.findElement(By.id("login-input")).sendKeys("MyLogin");
            driver.findElement(By.id("password-input")).clear();
            driver.findElement(By.id("password-input")).sendKeys("MyPass");
        } catch (Exception e) {
            // В случае возникновения ошибки делаем скриншот и добавляем его в отчет
            this.makeScreenshot(driver);
            throw e;
        }
    }

    @Test
    public void testTakeScreen() {
        driver.get("http://172.24.120.5:8081/login");
        this.setLoginAndPass();
//        driver.quit();
    }



    @Test
        void myTest ()  throws IOException{
            MyAllureSteps steps = new MyAllureSteps();
            steps.performAction(() -> {
                driver.get("http://172.24.120.5:8081/login");
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-input"))).clear();
                driver.findElement(By.id("login-input")).sendKeys("login");
                driver.findElement(By.id("password-input")).clear();
                driver.findElement(By.id("password-input")).sendKeys("password");
            }, "Шаг 1: Ввод логина и пароля");

            steps.performAction(() -> {
                driver.findElement(By.id("form_auth_button")).click();
            }, "Шаг 2: Нажатие кнопки Войти");

//            driver.quit();
//            this.getAttachmentWithArgs("exampleAllure.json");
            this.addText();
        }

}

