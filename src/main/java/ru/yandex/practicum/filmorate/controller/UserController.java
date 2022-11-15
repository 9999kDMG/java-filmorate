package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int id;

    @GetMapping
    public List<User> getUser() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@Valid @RequestBody User user) {
        log.info("Create User {}", user);
        throwIfLoginIncorrect(user);
        String newName;
        if (user.getName() == null || user.getName().isBlank()) {
            newName = user.getLogin();
        } else {
            newName = user.getName();
        }
        User newUser = user.withId(getNextId()).withName(newName);
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) {
        log.info("Update User {}", user);
        throwIfLoginIncorrect(user);
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("NOT_FOUND");
        }
        users.put(user.getId(), user);
        return user;
    }

    public void throwIfLoginIncorrect(User user) {
        if (user.getLogin().contains(" ")) {
            throw new BadRequestException("BAD_REQUEST");
        }
    }

    private int getNextId() {
        return ++id;
    }
}
