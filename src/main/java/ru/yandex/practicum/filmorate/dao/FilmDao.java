package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmDao {
    Optional<Film> findById(int id);

    List<Film> findAll();

    Optional<Film> putToStorage(Film film);

    Optional<Film> updateInStorage(Film film);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    List<Film> getMostPopularFilms(int limitSize);
}
