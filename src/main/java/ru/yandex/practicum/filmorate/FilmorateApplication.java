package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootApplication
public class FilmorateApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmorateApplication.class, args);
		User user = User.builder()
				.id(0)
				.email("12@yandex.ru")
				.login("login")
				.name("name")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		System.out.println(user);
	}

}
