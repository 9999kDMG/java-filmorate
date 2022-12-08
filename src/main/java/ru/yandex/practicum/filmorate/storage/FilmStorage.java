package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    int getNextId();

    Film getFilm(int id);

    List<Film> getAll();

    Film putFilm(Film film);

    void isExist(int filmId);
}
