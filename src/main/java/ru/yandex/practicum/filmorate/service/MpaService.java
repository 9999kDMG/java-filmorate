package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MpaService {
    private final MpaDao mpaDao;

    @Autowired
    public MpaService(MpaDao mpaDao) {
        this.mpaDao = mpaDao;
    }

    public List<Mpa> getAllMpa() {
        List<Optional<Mpa>> optMpa = mpaDao.getAll();
        return optMpa.stream().flatMap(Optional::stream).collect(Collectors.toList());
    }

    public Mpa getMpaById(int id) {
        return mpaDao.getById(id).orElseThrow(() -> new NotFoundException(String.format("mpa id%s", id)));
    }
}
