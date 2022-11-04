package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

    @GetMapping
    public List<Film> getFilm() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film postFilm(@Valid @RequestBody Film film) {
        log.info("Create Film" + film);
        validateFilmRelease(film.getReleaseDate());
        validateFilmId(film.getId());
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) {
        log.info("Update Film" + film);
        validateFilmRelease(film.getReleaseDate());
        if (!films.containsKey(film.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        films.put(film.getId(), film);
        return film;
    }

    public void validateFilmRelease(LocalDate date) {
        if (date.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void validateFilmId(int id) {
        if (!films.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


}
