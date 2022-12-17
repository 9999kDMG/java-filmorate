package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void deleteUser(int id);

    Optional<User> findById(int id);

    List<User> findAll();

    Optional<User> putToStorage(User user);

    Optional<User> updateInStorage(User user);

    void addFriend(int id, int friendId);

    void deleteFriend(int id, int friendId);

    List<User> getFriends(int id);

    List<User> getMutualFriends(int id, int otherId);
}
