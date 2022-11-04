package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.utils.ValidatorTest.userHasErrorMessage;

public class UserControllerTest {

    @ParameterizedTest(name = "{index}. Проверка невалидности email \"{arguments}\"")
    @ValueSource(strings = {"@yandex.ru", " ", "test.ru"})
    @DisplayName("Проверка невалидности email")
    void createEmailRequestTest(String value) {
        User user = User.builder()
                .id(0)
                .email(value)
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        Assertions.assertTrue(userHasErrorMessage(user, "email должен быть действительным"));
    }

    @ParameterizedTest(name = "{index}. Проверка невалидности id \"{arguments}\"")
    @ValueSource(ints = {-1, 0})
    @DisplayName("Проверка невалидности id")
    void createIdRequestTest(int value) {
        User user = User.builder()
                .id(value)
                .email("test@yandex.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        Assertions.assertTrue(userHasErrorMessage(user, "id должен быть положительным"));
    }

    @ParameterizedTest(name = "{index}. Проверка невалидности login \"{arguments}\"")
    @ValueSource(strings = {" ", "    ", ""})
    @DisplayName("Проверка невалидности login")
    void createLoginRequestTest(String value) {
        User user = User.builder()
                .id(0)
                .email("test@yandex.ru")
                .login(value)
                .name("name")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        Assertions.assertTrue(userHasErrorMessage(user, "login не должен состоять из пробелов или быть пустым"));
    }

    @Test
    @DisplayName("Проверка невалидности birthday")
    void createBirthdayRequestTest() {
        User user = User.builder()
                .id(0)
                .email("test@yandex.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2202, 1, 1))
                .build();
        Assertions.assertTrue(userHasErrorMessage(user, "дата рождения не может быть в будущем"));
    }
}
