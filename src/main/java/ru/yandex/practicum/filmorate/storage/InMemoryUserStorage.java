package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int id;

    @Override
    public int getNextId() {
        return ++id;
    }

    @Override
    public User getUser(int id) {
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User putUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User putUser(int id, User user) {
        users.put(id, user);
        return user;
    }

    @Override
    public void isExist(int userId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundException(String.format("user id%s", id));
        }
    }
}
