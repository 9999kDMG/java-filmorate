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
    public Optional<User> getUser(int id) {
        String sqlQuery = "SELECT * FROM USERS WHERE USER_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
        } catch (DataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<Optional<User>> getAll() {
        String sqlQuery = "SELECT * FROM USERS";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public Optional<User> createUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("user_id");
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("login", user.getLogin());
        userMap.put("name", user.getName());
        userMap.put("birthday", user.getBirthday());

        int userId = simpleJdbcInsert.executeAndReturnKey(userMap).intValue();
        return getUser(userId);
    }

    @Override
    public Optional<User> updateUser(User user) {
        String sqlQuery = "UPDATE USERS SET EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery
                , user.getEmail()
                , user.getLogin()
                , user.getName()
                , user.getBirthday()
                , user.getId());
        return getUser(user.getId());
    }

    @Override
    public Optional<User> deleteUser(int id) {
        String sqlQuery = "DELETE FROM USERS WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery, id);
        return Optional.empty();
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
    public List<Optional<User>> getFriends(int id) {
        String sqlQuery = "SELECT U.USER_ID, EMAIL, LOGIN, NAME, BIRTHDAY" +
                " FROM USERS U, FRIENDSHIP F WHERE U.USER_ID = ? AND F.USER_ID = U.USER_ID";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser, id);
    }

    @Override
    public List<Optional<User>> getMutualFriends(int id, int otherId) {
        String sqlQuery = "SELECT U.USER_ID, EMAIL, LOGIN, NAME, BIRTHDAY" +
                " FROM USERS U, FRIENDSHIP F1, FRIENDSHIP F2" +
                " WHERE F1.USER_ID = ? AND F2.USER_ID = ? AND F1.USER_ID = U.USER_ID AND F1.FRIEND_ID = F2.FRIEND_ID";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser, id, otherId);
    }

    private Optional<User> mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return Optional.ofNullable(User.builder()
                .id(resultSet.getInt("user_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build());
    }
}
