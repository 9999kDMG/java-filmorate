package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(int id) {
        String sqlQuery = "SELECT * FROM USERS WHERE USER_ID = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id));
        } catch (DataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sqlQuery = "SELECT * FROM USERS";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public Optional<User> putToStorage(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("user_id");
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("login", user.getLogin());
        userMap.put("name", user.getName());
        userMap.put("birthday", user.getBirthday());

        int userId = simpleJdbcInsert.executeAndReturnKey(userMap).intValue();
        return findById(userId);
    }

    @Override
    public Optional<User> updateInStorage(User user) {
        String sqlQuery = "UPDATE USERS SET EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery
                , user.getEmail()
                , user.getLogin()
                , user.getName()
                , user.getBirthday()
                , user.getId());
        return findById(user.getId());
    }

    @Override
    public void deleteUser(int id) {
        String sqlQuery = "DELETE FROM USERS WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public void addFriend(int id, int friendId) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("FRIENDSHIP");

        Map<String, Object> toFriendship = new HashMap<>();
        toFriendship.put("USER_ID", id);
        toFriendship.put("FRIEND_ID", friendId);
        simpleJdbcInsert.execute(toFriendship);
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        String sqlQuery = "DELETE FROM FRIENDSHIP WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }

    @Override
    public List<User> getFriends(int id) {
        String sqlQuery = "SELECT U.USER_ID, U.EMAIL, U.LOGIN, U.NAME, U.BIRTHDAY " +
                "FROM FRIENDSHIP F, USERS U WHERE F.USER_ID = ? AND U.USER_ID = F.FRIEND_ID";

        return jdbcTemplate.query(sqlQuery, this::mapRowToUser, id);
    }

    @Override
    public List<User> getMutualFriends(int id, int otherId) {
        String sqlQuery = "SELECT U.USER_ID, U.EMAIL, U.LOGIN, U.NAME, U.BIRTHDAY " +
                "FROM FRIENDSHIP AS F JOIN USERS AS U ON U.USER_ID = F.FRIEND_ID WHERE F.USER_ID = ? AND F.FRIEND_ID " +
                "IN (SELECT FRIEND_ID FROM FRIENDSHIP WHERE USER_ID = ?)";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser, id, otherId);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("user_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }
}
