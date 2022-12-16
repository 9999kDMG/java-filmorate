package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class MpaDaoImpl implements MpaDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Optional<Mpa>> getAll() {
        String sqlQuery = "SELECT * FROM MPA ORDER BY MPA_ID";
        return jdbcTemplate.query(sqlQuery, this::mapRowToRate);
    }

    @Override
    public Optional<Mpa> getById(int id) {
        String sqlQuery = "SELECT * FROM MPA WHERE MPA_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToRate, id);
        } catch (DataAccessException exception) {
            return Optional.empty();
        }
    }

    private Optional<Mpa> mapRowToRate(ResultSet resultSet, int rowNum) throws SQLException {
        return Optional.ofNullable(Mpa.builder()
                .id(resultSet.getInt("mpa_id"))
                .name(resultSet.getString("name"))
                .build());
    }
}
