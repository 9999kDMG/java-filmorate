package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@With
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {

    @PositiveOrZero(message = "id должен быть положительным")
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