package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmDao {
    Optional<Film> getFilm(int id);

    List<Optional<Film>> getAll();

    Optional<Film> createFilm(Film film);

    Optional<Film> updateFilm(Film film);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    List<Optional<Film>> getMostPopularFilms(int limitSize);
}
