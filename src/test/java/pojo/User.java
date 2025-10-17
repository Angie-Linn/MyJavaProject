package pojo;

import io.restassured.RestAssured;
import org.junit.Before;
import pojo.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.requestSpecification;
import static io.restassured.RestAssured.responseSpecification;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private String login;
    private String password;
    private String email;
    private List<Role> roles;
    private List <Note> notes;
    private User newUser;

    @Before
    public void before() {
        //Генерируем нового пользователя
        newUser = new User();
        newUser = newUser.generateUser();
    }

    public User generateUser() {
        int numberGenerated = 100 + (int) (Math.random() * 10000);

        //генерация нового юзера newUser
        User newUser = new User();
        newUser.setLogin("test_login" + numberGenerated);
        newUser.setPassword("123");
        newUser.setEmail("test" + numberGenerated + "@login.ru");
        newUser.setDefaultRoles();
        return newUser;
    }



    public void setDefaultRoles() {
        Role defaultRole = new Role();
        defaultRole.setId("2");
        defaultRole.setName("ROLE_USER");

        List<Role> defaultListRole = new ArrayList<>();
        defaultListRole.add(defaultRole);

        this.roles = defaultListRole;
    }

    public void setDefaultNotes() {
        Note defaultNote = new Note();
        defaultNote.setName("Podpricritus");
        defaultNote.setContent("Poluelf");
        defaultNote.setColor("#ccff90");
        defaultNote.setPriority("8");

        List<Note> defaultListNote = new ArrayList<>();
        defaultListNote.add(defaultNote);

        this.notes = defaultListNote;
    }




}
