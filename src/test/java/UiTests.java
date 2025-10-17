import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class UiTests extends BaseTest{
    @Attachment(value = "Attachment Screenshot", type = "image/png")
    public byte[] makeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
    @AfterEach
    public void afterTest(){makeScreenshot(driver);}

    private String userLogin = "test_user_10";

    // Методы-шаги для теста mainPage
    @Step("Переход на страницу авторизации")
    public void stepGoToAuthorizationPage() {
        authorizationPage.goToAuthorizationPage();
    }

    @Step("Ввод логина: \"{login}\"")
    public void stepFillInLogin(String login) {
        authorizationPage.fillInLogin(login);
    }

    @Step("Ввод пароля")
    public void stepFillInPassword(String password) {
        authorizationPage.fillInPassword(password);
    }

    @Step("Нажатие кнопки \"Войти\"")
    public void stepClickLoginButton() {
        authorizationPage.clickLoginButton();
    }

    @Step("Нажатие кнопки \"Домой\"")
    public void stepClickHomeButton() {
        mainPage.clickHomeButton();
    }

    @Step("Проверка отображения кнопки \"Выйти\"")
    public void stepVerifyLogoutButtonIsDisplayed() {
        assertEquals(true, mainPage.logoutButtonIsDisplayed());
        System.out.println("✅ Успех: вход выполнен");
    }

    @Step("Выход из системы")
    public void stepLogout() {
        mainPage.logout();
    }

    @Step("Нажатие кнопки подтверждения выхода")
    public void stepSetExitButton() {
        mainPage.setExitButton();
    }

    @Step("Проверка отображения кнопки \"Войти\" после выхода")
    public void stepVerifyLoginButtonIsDisplayed() {
        Assertions.assertEquals(true, authorizationPage.loginButtonIsDisplayed());
        System.out.println("✅ Успех: выход выполнен");
    }

    //  Методы-шаги для теста createNewNote
    @Step("Создание новой заметки")
    public void stepCreateNote() {
        mainPage.createNote();
    }

    @Step("Установка заголовка заметки")
    public void stepSetNoteTitle() {
        mainPage.setNoteTitle();
    }

    @Step("Сохранение заметки")
    public void stepSaveNote() {
        mainPage.setSaveNote();
    }

    @Step("Получение и проверка заголовка заметки: \"{expectedTitle}\"")
    public void stepGetAndVerifyNoteTitle(String expectedTitle) {
        String titleOfNote = driver.findElement(By.xpath("//p[contains(@id, 'note-title')]")).getText();
        Assertions.assertEquals(expectedTitle, titleOfNote, "Заметка не найдена");
        System.out.println("✅ Успех: Заметка с текстом '" + titleOfNote + "' найдена");
    }

    @Test
    @DisplayName("UI тест: Авторизация и выход из системы")
    @Description("Проверяет успешный вход пользователя в систему, переход на главную страницу и корректный выход.")
    public void mainPage () {
        stepGoToAuthorizationPage();
        stepFillInLogin(userLogin);
        stepFillInPassword("test");
        stepClickLoginButton();
        stepClickHomeButton();
        stepVerifyLogoutButtonIsDisplayed();
        stepLogout();
        stepSetExitButton();
        stepVerifyLoginButtonIsDisplayed();
    }

    @Test
    @DisplayName("UI тест: Создание новой заметки")
    @Description("Проверяет возможность пользователя авторизоваться и успешно создать новую заметку на платформе.")
    public void createNewNote () {
        stepGoToAuthorizationPage();
        stepFillInLogin(userLogin);
        stepFillInPassword("test");
        stepClickLoginButton();
        stepCreateNote();
        stepSetNoteTitle();
        stepSaveNote();
        stepGetAndVerifyNoteTitle("хоть бы получилось");
    }

    @AfterEach
    public void after() {
        executeQuery("DELETE FROM nfaut.notes WHERE name = 'хоть бы получилось'");
    }

    public void executeQuery(String sql) {
        try {
            String url = "jdbc:postgresql://172.24.120.5:5432/postgres";
            String login = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(url, login, password);
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
            } finally {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}