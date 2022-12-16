package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> deleteUser(int id);

    Optional<User> getUser(int id);

    List<Optional<User>> getAll();

    Optional<User> createUser(User user);

    Optional<User> updateUser(User user);

    void addFriend(int id, int friendId);

    void deleteFriend(int id, int friendId);

    List<Optional<User>> getFriends(int id);

    List<Optional<User>> getMutualFriends(int id, int otherId);
}
