package ru.yandex.practicum.filmorate.dao;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.Collections;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDaoTest {
    private final FilmDao filmDao;
    private final Film film1 = Film.builder()
            .id(1)
            .name("name")
            .description("")
            .releaseDate(LocalDate.of(2000, 1, 1))
            .duration(120)
            .mpa(new Mpa(1, "G"))
            .genres(Collections.emptyList())
            .build();

    private final Film film2 = Film.builder()
            .id(1)
            .name("name")
            .description("")
            .releaseDate(LocalDate.of(2000, 1, 1))
            .duration(120)
            .mpa(new Mpa(1, "G"))
            .genres(Collections.emptyList())
            .build();

    @Test
    void getByIdTestShouldBeTrue() {
        Assertions.assertEquals(filmDao.createFilm(film1).get(), film1);
    }

    @Test
    void updateFilmTestShouldTrue() {
        filmDao.createFilm(film1);
        Assertions.assertEquals(filmDao.updateFilm(film2).get(), film2);
    }
}
