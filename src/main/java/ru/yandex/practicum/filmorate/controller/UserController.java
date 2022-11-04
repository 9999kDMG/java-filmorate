package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

    @GetMapping
    public List<User> getUser() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) {
        log.info("Create User" + user);
        validateUserId(user.getId());
        checkUserName(user);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) {
        log.info("Update User" + user);
        if (!users.containsKey(user.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        users.put(user.getId(), user);
        return user;
    }

    public void checkUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    public void validateUserId(int id) {
        if (!users.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
