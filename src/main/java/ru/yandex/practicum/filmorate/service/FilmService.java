package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film getFilmById(int filmId) {
        filmStorage.isExist(filmId);
        return filmStorage.getFilm(filmId);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film createFilm(Film film) {
        Film newFilm = film.withId(filmStorage.getNextId());
        return filmStorage.putFilm(newFilm);
    }

    public Film updateFilm(Film film) {
        filmStorage.isExist(film.getId());
        return filmStorage.putFilm(film);
    }

    public Film addLike(int filmId, int userId) {
        filmStorage.isExist(filmId);
        userStorage.isExist(userId);
        Film newFilm = filmStorage.getFilm(filmId);
        newFilm.addFilmLike(userId);
        return filmStorage.putFilm(newFilm);
    }

    public Film deleteLike(int filmId, int userId) {
        filmStorage.isExist(filmId);
        userStorage.isExist(userId);
        Film newFilm = filmStorage.getFilm(filmId);
        newFilm.deleteLike(userId);
        return filmStorage.putFilm(newFilm);
    }

    public List<Film> getMostPopularFilms(int limitSize) {
        return filmStorage.getAll().stream().sorted((a, b) ->
                b.getFilmLikes().size() - a.getFilmLikes().size()
        ).limit(limitSize).collect(Collectors.toList());
    }
}