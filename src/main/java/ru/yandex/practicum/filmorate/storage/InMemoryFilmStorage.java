package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id;

    @Override
    public int getNextId() {
        return ++id;
    }

    @Override
    public Film getFilm(int id) {
        return films.get(id);
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film putFilm(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void isExist(int filmId) {
        if (!films.containsKey(filmId)) {
            throw new NotFoundException(String.format("film id%s", id));
        }
    }
}