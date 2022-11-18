package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    public int getNextId();

    public User getUser(int id);

    public List<User> getAll();

    public User putUser(User user);

    public User putUser(int id, User user);

    public boolean isUserInStorage(int userId);
}
