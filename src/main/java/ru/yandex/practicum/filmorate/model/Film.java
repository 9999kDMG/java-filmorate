package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@With
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {
    private List<Genre> genres;

    private Mpa mpa;

    @PositiveOrZero(message = "id должен быть положительным")
    private int id;

    @NotBlank(message = "название не должно состоять из пробелов или быть пустым")
    private String name;

    @NotBlank(message = "описание не должно состоять из пробелов или быть пустым")
    @Size(max = 200, message = "описание не должно превишать 200 символов")
    private String description;

    @PastOrPresent(message = "дата выхода фильма не должна быть в будущем")
    private LocalDate releaseDate;

    @Positive(message = "продолжительность должна быть положительной")
    private int duration;
}
