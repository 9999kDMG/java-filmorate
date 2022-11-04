package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.utils.ValidatorTest.userHasErrorMessage;

public class FilmControllerTest {

    @ParameterizedTest(name = "{index}. Проверка невалидности id \"{arguments}\"")
    @ValueSource(ints = {-1, 0})
    @DisplayName("Проверка невалидности id")
    void createIdRequestTest(int value) {
        Film film = Film.builder()
                .id(value)
                .name("name")
                .description("")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(120)
                .build();
        Assertions.assertTrue(userHasErrorMessage(film, "id должен быть положительным"));
    }

    @ParameterizedTest(name = "{index}. Проверка невалидности name \"{arguments}\"")
    @ValueSource(strings = {"", " ", "             "})
    @DisplayName("Проверка невалидности name")
    void createNameRequestTest(String value) {
        Film film = Film.builder()
                .id(0)
                .name(value)
                .description("")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(120)
                .build();
        Assertions.assertTrue(userHasErrorMessage(film, "название не должно состоять из пробелов или быть пустым"));
    }

    @ParameterizedTest(name = "{index}. Проверка невалидности description \"{arguments}\"")
    @ValueSource(strings = {"", " ", "             "})
    @DisplayName("Проверка невалидности description")
    void createDescriptionRequestTest(String value) {
        Film film = Film.builder()
                .id(0)
                .name("name")
                .description(value)
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(120)
                .build();
        Assertions.assertTrue(userHasErrorMessage(film, "описание не должно состоять из пробелов или быть пустым"));
    }

    @Test
    @DisplayName("Проверка невалидности releaseDate")
    void createReleaseDateRequestTest() {
        Film film = Film.builder()
                .id(0)
                .name("name")
                .description("")
                .releaseDate(LocalDate.of(2200, 1, 1))
                .duration(120)
                .build();
        Assertions.assertTrue(userHasErrorMessage(film, "дата выхода фильма не должна быть в будущем"));
    }

    @ParameterizedTest(name = "{index}. Проверка невалидности duration \"{arguments}\"")
    @ValueSource(ints = {-1, 0})
    @DisplayName("Проверка невалидности description")
    void createDurationRequestTest(int value) {
        Film film = Film.builder()
                .id(0)
                .name("name")
                .description("")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(value)
                .build();
        Assertions.assertTrue(userHasErrorMessage(film, "продолжительность должна быть положительной"));
    }
}
