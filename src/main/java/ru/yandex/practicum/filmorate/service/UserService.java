package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAll() {
        List<Optional<User>> optUser = userDao.getAll();
        return optUser.stream().flatMap(Optional::stream).collect(Collectors.toList());
    }

    public User createUser(User user) {
        String newName;
        if (user.getName() == null || user.getName().isBlank()) {
            newName = user.getLogin();
        } else {
            newName = user.getName();
        }
        User newUser = user.withName(newName);
        return userDao.createUser(newUser)
                .orElseThrow(() -> new NotFoundException(String.format("user id%s", user.getId())));
    }

    public User updateUser(User user) {
        return userDao.updateUser(user)
                .orElseThrow(() -> new NotFoundException(String.format("user id%s", user.getId())));
    }

    public User getUserById(int id) {
        return userDao.getUser(id).orElseThrow(() -> new NotFoundException(String.format("user id%s", id)));
    }

    public void addUserFriend(int id, int friendId) {
        getUserById(id);
        getUserById(friendId);

        userDao.addFriend(id, friendId);
    }

    public void deleteUserFriend(int id, int friendId) {
        getUserById(id);
        getUserById(friendId);

        userDao.deleteFriend(id, friendId);
    }

    public List<User> getFriendsList(int id) {
        getUserById(id);
        List<Optional<User>> optUser = userDao.getFriends(id);
        return optUser.stream().flatMap(Optional::stream).collect(Collectors.toList());
    }

    public List<User> getMutualFriends(int id, int otherId) {
        getUserById(id);
        getUserById(otherId);

        List<Optional<User>> optUser = userDao.getMutualFriends(id, otherId);
        return optUser.stream().flatMap(Optional::stream).collect(Collectors.toList());
    }

    public void deleteUser(int id) {
        getUserById(id);
        userDao.deleteUser(id);
    }
}
