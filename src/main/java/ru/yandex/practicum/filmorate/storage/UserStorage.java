package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    int getNextId();

    User getUser(int id);

    List<User> getAll();

    User putUser(User user);

    User putUser(int id, User user);

    void isExist(int userId);
}
