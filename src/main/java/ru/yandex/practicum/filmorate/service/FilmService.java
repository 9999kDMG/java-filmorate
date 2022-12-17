package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Service
public class FilmService {
    private final FilmDao filmDao;
    private final UserDao userDao;
    private final MpaDao mpaDao;
    private final GenreDao genreDao;

    public FilmService(FilmDao filmDao, UserDao userDao, MpaDao mpaDao, GenreDao genreDao) {
        this.filmDao = filmDao;
        this.userDao = userDao;
        this.mpaDao = mpaDao;
        this.genreDao = genreDao;
    }

    public Film getFilmById(int filmId) {
        return filmDao.findById(filmId)
                .orElseThrow(() -> new NotFoundException(String.format("film id%s", filmId)));
    }

    public List<Film> getAll() {
        return filmDao.findAll();
    }

    public Film createFilm(Film film) {
        throwIfNFMpaOrGenre(film);
        return filmDao.putToStorage(film)
                .orElseThrow(() -> new NotFoundException(String.format("film id%s", film.getId())));
    }

    public Film updateFilm(Film film) {
        getFilmById(film.getId());
        throwIfNFMpaOrGenre(film);
        return filmDao.updateInStorage(film)
                .orElseThrow(() -> new NotFoundException(String.format("film id%s", film.getId())));

    }

    private void throwIfNFMpaOrGenre(Film film) {
        mpaDao.getById(film.getMpa().getId())
                .orElseThrow(() -> new NotFoundException(String.format("mpa id%s", film.getMpa().getId())));
    }

    public void addLike(int filmId, int userId) {
        getFilmById(filmId);
        userDao.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("user id%s", userId)));
        filmDao.addLike(filmId, userId);
    }

    public void deleteLike(int filmId, int userId) {
        getFilmById(filmId);
        userDao.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("user id%s", userId)));
        filmDao.deleteLike(filmId, userId);
    }

    public List<Film> getMostPopularFilms(int limitSize) {
        return filmDao.getMostPopularFilms(limitSize);
    }
}