package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Genre {
    private int id;
    private String name;
}
