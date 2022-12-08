package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@With
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    final Set<Integer> friends = new HashSet<>();

    @PositiveOrZero(message = "id должен быть положительным")
    int id;

    @Email(message = "email должен быть действительным")
    String email;

    @NotBlank(message = "login не должен состоять из пробелов или быть пустым")
    String login;

    String name;

    @PastOrPresent(message = "дата рождения не может быть в будущем")
    LocalDate birthday;

    public void addFriend(int friendId) {
        friends.add(friendId);
    }

    public void deleteFriend(int friendId) {
        friends.remove(friendId);
    }
}
