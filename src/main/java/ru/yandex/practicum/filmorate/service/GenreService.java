package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DaoGenre;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final DaoGenre daoGenre;

    @Autowired
    public GenreService(DaoGenre daoGenre) {
        this.daoGenre = daoGenre;
    }

    public Genre getGenreById(int id) {
        return daoGenre.getById(id).orElseThrow(() -> new NotFoundException(String.format("genre id%s", id)));
    }

    public List<Genre> getAllGenres() {
        List<Optional<Genre>> optGenre = daoGenre.getAll();
        return optGenre.stream().flatMap(Optional::stream).collect(Collectors.toList());
    }
}
