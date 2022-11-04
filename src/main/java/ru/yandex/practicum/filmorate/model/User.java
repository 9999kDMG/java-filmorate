package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@Builder
public class User {

    @Positive(message = "id должен быть положительным")
    int id;

    @Email(message = "email должен быть действительным")
    String email;

    @NotBlank(message = "login не должен состоять из пробелов или быть пустым")
    String login;

    String name;

    @PastOrPresent(message = "дата рождения не может быть в будущем")
    LocalDate birthday;
}
