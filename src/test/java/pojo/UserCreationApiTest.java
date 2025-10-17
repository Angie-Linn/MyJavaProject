package pojo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dto.UserCreationDTO;

import java.util.UUID;

public class UserCreationApiTest {
    // ObjectMapper для конвертации объектов в JSON строки
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // прикрепление для тела запроса
    @Attachment(value = "Request Body", type = "application/json", fileExtension = ".json")
    public String attachRequestJsonBody(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            return "Failed to serialize object to JSON: " + e.getMessage();
        }
    }

    // прикрепление для тела ответа
    @Attachment(value = "Response Body", type = "application/json", fileExtension = ".json")
    public String attachResponseJsonBody(String responseBody) {
        return responseBody;
    }

    //  Методы-шаги для теста testUserRegistration
    @Step("Генерация уникального логина для теста")
    public String stepGenerateUniqueLogin() {
        String uniqueLogin = "podpricrytus" + System.currentTimeMillis();
        System.out.println("Сгенерирован логин: " + uniqueLogin);
        return uniqueLogin;
    }

    @Step("Создание объекта User с логином: \"{login}\"")
    public User stepCreateUserObject(String login) {
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword("123");
        newUser.setEmail("test@login.ru");
        newUser.setDefaultRoles();
        newUser.setDefaultNotes();
        return newUser;
    }

    @Step("Создание объекта UserCreationDTO из User")
    public UserCreationDTO stepCreateUserCreationDTO(User user) {
        UserCreationDTO userCreationDTO = new UserCreationDTO();
        userCreationDTO.setLogin(user.getLogin());
        userCreationDTO.setPassword(user.getPassword());
        userCreationDTO.setEmail(user.getEmail());
        userCreationDTO.setRoles(user.getRoles());
        userCreationDTO.setNotes(user.getNotes());
        attachRequestJsonBody(userCreationDTO);// ЗДЕСЬ ИДЕТ ВЫЗОВ ДЛЯ ПРИКРЕПЛЕНИЯ ТЕЛА ЗАПРОСА
        return userCreationDTO;
    }

    @Step("Отправка POST запроса на регистрацию пользователя")
    public void stepSendRegistrationRequest(UserCreationDTO userCreationDTO) {
        Response response = RestAssured.given().log().all()
                .body(userCreationDTO)
                .contentType("application/json")
                .post("http://172.24.120.5:8082/api/registration")
                .then().log().all()
                .statusCode(201) // Проверка статуса в шаге, так как это часть запроса
                .extract().response(); // Извлекаем ответ для дальнейших действий
        attachResponseJsonBody(response.getBody().asString()); // Прикрепляем тело ответа
    }

    //  Тестовый метод

    @Test
    @DisplayName("API тест: успешная регистрация нового пользователя")
    @Description("Проверяет, что система успешно регистрирует нового пользователя с уникальным логином и возвращает статус 201 Created. В отчет Allure прикрепляются тела запроса и ответа.")
    public void testUserRegistration() {
        String uniqueLogin = stepGenerateUniqueLogin();

        User newUser = stepCreateUserObject(uniqueLogin);

        UserCreationDTO userCreationDTO = stepCreateUserCreationDTO(newUser);

        stepSendRegistrationRequest(userCreationDTO);
    }
}