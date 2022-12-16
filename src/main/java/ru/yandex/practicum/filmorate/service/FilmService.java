package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DaoGenre;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmDao filmDao;
    private final UserDao userDao;
    private final MpaDao mpaDao;
    private final DaoGenre daoGenre;

    public FilmService(FilmDao filmDao, UserDao userDao, MpaDao mpaDao, DaoGenre daoGenre) {
        this.filmDao = filmDao;
        this.userDao = userDao;
        this.mpaDao = mpaDao;
        this.daoGenre = daoGenre;
    }

    public Film getFilmById(int filmId) {
        return filmDao.getFilm(filmId).orElseThrow(() -> new NotFoundException(String.format("film id%s", filmId)));
    }

    public List<Film> getAll() {
        List<Optional<Film>> optFilm = filmDao.getAll();
        return optFilm.stream().flatMap(Optional::stream).collect(Collectors.toList());
    }

    public Film createFilm(Film film) {
        throwIfNFMpaOrGenre(film);
        return filmDao.createFilm(film)
                .orElseThrow(() -> new NotFoundException(String.format("film id%s", film.getId())));
    }

    public Film updateFilm(Film film) {
        getFilmById(film.getId());
        throwIfNFMpaOrGenre(film);
        return filmDao.updateFilm(film)
                .orElseThrow(() -> new NotFoundException(String.format("film id%s", film.getId())));

    }

    private void throwIfNFMpaOrGenre(Film film) {
        mpaDao.getById(film.getMpa().getId())
                .orElseThrow(() -> new NotFoundException(String.format("mpa id%s", film.getMpa().getId())));
//        film.getGenres().forEach(genre -> {
//            if (daoGenre.getById(genre.getId()).isEmpty()) {
//                throw new NotFoundException(String.format("genre id%s", genre.getId()));
//            }
//        });
    }

    public void addLike(int filmId, int userId) {
        getFilmById(filmId);
        userDao.getUser(userId).orElseThrow(() -> new NotFoundException(String.format("user id%s", userId)));
        filmDao.addLike(filmId, userId);
    }

    public void deleteLike(int filmId, int userId) {
        getFilmById(filmId);
        userDao.getUser(userId).orElseThrow(() -> new NotFoundException(String.format("user id%s", userId)));
        filmDao.deleteLike(filmId, userId);
    }

    public List<Film> getMostPopularFilms(int limitSize) {
        List<Optional<Film>> optFilm = filmDao.getMostPopularFilms(limitSize);
        return optFilm.stream().flatMap(Optional::stream).collect(Collectors.toList());
    }
}