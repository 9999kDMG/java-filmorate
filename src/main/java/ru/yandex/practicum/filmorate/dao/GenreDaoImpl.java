package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Genre> getById(int filmId) {
        String sqlQuery = "SELECT * FROM GENRE WHERE GENRE_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, filmId);
        } catch (DataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<Optional<Genre>> getAll() {
        String sqlQuery = "SELECT * FROM GENRE ORDER BY GENRE_ID";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public List<Optional<Genre>> getAllByIdFilm(int filmId) {
        String sqlQuery = "SELECT FG.GENRE_ID AS genre_id, G.NAME AS name " +
                "FROM FILM_GENRES AS FG JOIN GENRE AS G ON FG.GENRE_ID = G.GENRE_ID " +
                "WHERE FG.FILM_ID = ?";
        try {
            return jdbcTemplate.query(sqlQuery, this::mapRowToGenre, filmId);
        } catch (DataAccessException exception) {
            return List.of(Optional.empty());
        }
    }

    private Optional<Genre> mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Optional.ofNullable(Genre.builder()
                .id(resultSet.getInt("genre_id"))
                .name(resultSet.getString("name"))
                .build());
    }
}
