package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

    private int id;

    @GetMapping
    public List<Film> getFilm() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film postFilm(@Valid @RequestBody Film film) {
        log.info("Create Film {}", film);
        throwIfFilmReleaseIncorrect(film.getReleaseDate());
        Film newFilm = film.withId(getNextId());
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) {
        log.info("Update Film {}", film);
        throwIfFilmReleaseIncorrect(film.getReleaseDate());
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("NOT_FOUND");
        }
        films.put(film.getId(), film);
        return film;
    }

    public void throwIfFilmReleaseIncorrect(LocalDate date) {
        if (date.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new BadRequestException("BAD_REQUEST");
        }
    }

    private int getNextId() {
        return ++id;
    }
}
