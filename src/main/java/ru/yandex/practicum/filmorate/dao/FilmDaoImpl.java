package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FilmDaoImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;
    private final GenreDao genreDao;

    @Autowired
    public FilmDaoImpl(JdbcTemplate jdbcTemplate, GenreDao genreDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDao = genreDao;
    }

    @Override
    public Optional<Film> findById(int id) {
        String sqlQuery = "SELECT * FROM FILM F, MPA M WHERE F.FILM_ID = ? AND F.MPA_ID = M.MPA_ID";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id));
        } catch (DataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<Film> findAll() {
        String sqlQuery = "SELECT * FROM FILM F, MPA M WHERE F.MPA_ID = M.MPA_ID";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public Optional<Film> putToStorage(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILM")
                .usingGeneratedKeyColumns("film_id");
        Map<String, Object> filmMap = new HashMap<>();
        filmMap.put("name", film.getName());
        filmMap.put("description", film.getDescription());
        filmMap.put("release_date", film.getReleaseDate());
        filmMap.put("duration", film.getDuration());
        filmMap.put("mpa_id", film.getMpa().getId());

        int filmId = simpleJdbcInsert.executeAndReturnKey(filmMap).intValue();
        updateFilmGenres(film.getGenres(), filmId);
        return findById(filmId);
    }

    @Override
    public Optional<Film> updateInStorage(Film film) {
        String sqlQuery = "UPDATE FILM SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, MPA_ID = ?" +
                " WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        jdbcTemplate.update("DELETE FROM FILM_GENRES WHERE FILM_ID = ?", film.getId());
        updateFilmGenres(film.getGenres(), film.getId());
        return findById(film.getId());
    }

    @Override
    public void addLike(int filmId, int userId) {
        String sqlQuery = "INSERT INTO LIKES VALUES(?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        String sqlQuery = "DELETE FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public List<Film> getMostPopularFilms(int limitSize) {
        String sqlQuery = "SELECT F.FILM_ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION, M.MPA_ID AS mpa_id, " +
                "M.NAME AS mpa_name, G.GENRE_ID, G.NAME AS genre_name, " +
                "COUNT(L.FILM_ID) AS likes " +
                "FROM FILM AS F " +
                "INNER JOIN MPA M ON F.MPA_ID = M.MPA_ID " +
                "LEFT JOIN FILM_GENRES FG ON F.FILM_ID = FG.FILM_ID " +
                "LEFT JOIN GENRE G ON FG.GENRE_ID = G.GENRE_ID " +
                "LEFT JOIN LIKES L ON F.FILM_ID = L.FILM_ID " +
                "GROUP BY F.FILM_ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION, M.MPA_ID, M.NAME, G.GENRE_ID, G.NAME " +
                "ORDER BY likes DESC, F.NAME " +
                "LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm, Math.max(limitSize, 0));
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("film_id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(new Mpa(resultSet.getInt("mpa.mpa_id"), resultSet.getString("mpa.name")))
                .genres(genreDao.getAllByIdFilm(resultSet.getInt("film_id"))
                        .stream().flatMap(Optional::stream).collect(Collectors.toList()))
                .build();
    }

    private void updateFilmGenres(List<Genre> genres, int filmId) {
        if (genres == null) {
            return;
        }

        List<Integer> genreUniqueIds = genres.stream()
                .map(Genre::getId)
                .distinct()
                .collect(Collectors.toList());

        String sqlQuery = "INSERT INTO FILM_GENRES VALUES(?, ?)";
        jdbcTemplate.batchUpdate(
                sqlQuery,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        int genreId = genreUniqueIds.get(i);
                        ps.setInt(1, filmId);
                        ps.setInt(2, genreId);
                    }

                    public int getBatchSize() {
                        return genreUniqueIds.size();
                    }
                });
    }
}
