package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
public class Film {

    @Positive(message = "id должен быть положительным")
    int id;

    @NotBlank(message = "название не должно состоять из пробелов или быть пустым")
    String name;

    @NotBlank(message = "описание не должно состоять из пробелов или быть пустым")
    @Size(max = 200, message = "описание не должно превишать 200 символов")
    String description;

    @PastOrPresent(message = "дата выхода фильма не должна быть в будущем")
    LocalDate releaseDate;

    @Positive(message = "продолжительность должна быть положительной")
    int duration;
}
