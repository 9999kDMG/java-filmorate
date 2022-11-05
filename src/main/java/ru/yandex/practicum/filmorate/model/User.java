package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@With
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @PositiveOrZero(message = "id должен быть положительным")
    int id;

    @Email(message = "email должен быть действительным")
    String email;

    @NotBlank(message = "login не должен состоять из пробелов или быть пустым")
    String login;

    String name;

    @PastOrPresent(message = "дата рождения не может быть в будущем")
    LocalDate birthday;
}
