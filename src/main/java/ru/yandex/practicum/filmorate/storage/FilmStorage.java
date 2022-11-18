package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    public int getNextId();

    public Film getFilm(int id);

    public List<Film> getAll();

    public Film putFilm(Film film);

    public boolean isFilmInStorage(int filmId);
}
