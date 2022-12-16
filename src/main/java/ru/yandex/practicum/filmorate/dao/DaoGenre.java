package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface DaoGenre {
    List<Optional<Genre>> getAll();

    Optional<Genre> getById(int filmId);

    List<Optional<Genre>> getAllByIdFilm(int filmId);
}