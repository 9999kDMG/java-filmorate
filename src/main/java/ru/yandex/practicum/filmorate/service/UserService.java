package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User createUser(User user) {
        String newName;
        if (user.getName() == null || user.getName().isBlank()) {
            newName = user.getLogin();
        } else {
            newName = user.getName();
        }
        User newUser = user.withId(userStorage.getNextId()).withName(newName);
        return userStorage.putUser(newUser);
    }

    public User updateUser(User user) {
        userStorage.isExist(user.getId());
        return userStorage.putUser(user);
    }

    public User getUserById(int id) {
        userStorage.isExist(id);
        return userStorage.getUser(id);
    }

    public User addUserFriend(int id, int friendId) {
        userStorage.isExist(id);
        userStorage.isExist(friendId);
        User newUser = userStorage.getUser(id);
        newUser.addFriend(friendId);

        User newFriend = userStorage.getUser(friendId);
        newFriend.addFriend(id);
        userStorage.putUser(friendId, newFriend);

        return userStorage.putUser(id, newUser);
    }

    public User deleteUserFriend(int id, int friendId) {
        userStorage.isExist(id);
        userStorage.isExist(friendId);
        User newUser = userStorage.getUser(id);
        newUser.deleteFriend(friendId);

        User newFriend = userStorage.getUser(friendId);
        newFriend.deleteFriend(id);
        userStorage.putUser(friendId, newFriend);

        return userStorage.putUser(id, newUser);
    }

    public List<User> getFriendsList(int id) {
        userStorage.isExist(id);
        List<Integer> friends = new ArrayList<>(userStorage.getUser(id).getFriends());
        List<User> friendsList = new ArrayList<>();
        for (int userId : friends) {
            friendsList.add(userStorage.getUser(userId));
        }
        return friendsList;
    }

    public List<User> getMutualFriends(int id, int otherId) {
        userStorage.isExist(id);
        userStorage.isExist(otherId);
        List<Integer> friendsOfFirstUser = new ArrayList<>(userStorage.getUser(id).getFriends());
        List<Integer> friendsOfSecondUser = new ArrayList<>(userStorage.getUser(otherId).getFriends());
        List<Integer> mutualFriendsId = friendsOfFirstUser
                .stream().filter(friendsOfSecondUser::contains).collect(Collectors.toList());
        List<User> mutualFriends = new ArrayList<>();
        for (int userId : mutualFriendsId) {
            mutualFriends.add(userStorage.getUser(userId));
        }
        return mutualFriends;
    }
}
