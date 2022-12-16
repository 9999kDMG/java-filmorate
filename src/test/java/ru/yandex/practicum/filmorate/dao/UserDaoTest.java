package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDaoTest {
    private final UserDao userDao;
    private final User user1 = User.builder()
            .id(1)
            .email("test@yandex.ru")
            .login("login1")
            .name("name")
            .birthday(LocalDate.of(2000, 1, 1))
            .build();
    private final User user2 = User.builder()
            .id(1)
            .email("test2@yandex.ru")
            .login("login2")
            .name("name2")
            .birthday(LocalDate.of(2001, 1, 1))
            .build();

    @Test
    void getByIdTestShouldBeTrue() {
        Assertions.assertEquals(userDao.createUser(user1).get(), user1);
    }

    @Test
    void deleteUserByIdTestShouldBeTrue() {
        userDao.deleteUser(1);
        Assertions.assertEquals(List.of(), userDao.getAll());
    }

    @Test
    void updateUserTestShouldTrue() {
        userDao.createUser(user1);
        Assertions.assertEquals(userDao.updateUser(user2).get(), user2);
    }
}
