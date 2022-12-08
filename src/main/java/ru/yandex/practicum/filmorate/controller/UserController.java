package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@Valid @RequestBody User user) {
        log.info("Create User {}", user);
        throwIfLoginIncorrect(user);
        return userService.createUser(user);
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) {
        log.info("Update User {}", user);
        throwIfLoginIncorrect(user);
        return userService.updateUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("Get User by id {}", id);
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Add friend id {} to User id {}", friendId, id);
        return userService.addUserFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Delete friend id {} from User id {}", friendId, id);
        return userService.deleteUserFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendsList(@PathVariable int id) {
        log.info("Get friends list User id {}", id);
        return userService.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Get common friends Users id {} and {}", id, otherId);
        return userService.getMutualFriends(id, otherId);
    }

    public void throwIfLoginIncorrect(User user) {
        if (user.getLogin().contains(" ")) {
            throw new BadRequestException("invalid user login");
        }
    }
}
