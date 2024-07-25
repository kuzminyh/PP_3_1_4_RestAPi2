package ru.kata.spring.boot_security.demo.Configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.Model.Role;
import ru.kata.spring.boot_security.demo.Model.User;
import ru.kata.spring.boot_security.demo.Service.UserService;

import javax.transaction.Transactional;
import java.util.Collections;

@Configuration
public class DataLoader {

    private final UserService userService;

    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            insertUsers();
        };
    }

    @Transactional
    public void insertUsers() {
        if (userService.allUsers().isEmpty())   {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setEmail("admin@mail.ru");
            admin.setRoles(Collections.singleton(new Role(2L)));

            User user = new User();
            user.setUsername("user");
            user.setPassword("user");
            user.setEmail("user@mail.ru");
            user.setRoles(Collections.singleton(new Role(1L)));

            userService.saveUser(admin);
            userService.saveUser(user);
        }
    }


}
