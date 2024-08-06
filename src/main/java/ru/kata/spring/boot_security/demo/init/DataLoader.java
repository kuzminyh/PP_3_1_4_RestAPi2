package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.RoleDAO;
import ru.kata.spring.boot_security.demo.DAO.UserDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import java.util.Collections;

@Component
public class DataLoader {

    @PersistenceContext
    private EntityManager em;

    private final RoleService roleService;

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DataLoader(RoleService roleService, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
        if (roleService.getRoles().isEmpty()) {
            Role admin = new Role();
            admin.setName("ROLE_ADMIN");

            Role user = new Role();
            user.setName("ROLE_USER");

            roleService.saveRole(admin);
            roleService.saveRole(user);
        }

        if (userService.allUsers().isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setEmail("admin@mail.ru");
            admin.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));
            User user = new User();
            user.setUsername("user");
            user.setPassword("user");
            user.setEmail("user@mail.ru");
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));

            userService.saveUser(admin);
            userService.saveUser(user);
        }
        };
    }
}
