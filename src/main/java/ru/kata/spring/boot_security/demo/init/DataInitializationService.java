package ru.kata.spring.boot_security.demo.init;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.RoleDAO;
import ru.kata.spring.boot_security.demo.DAO.UserDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;

@Service
public class DataInitializationService {

    @PersistenceContext
    private EntityManager em;

    private final RoleDAO roleDAO;

    private final UserDAO userDAO;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DataInitializationService(RoleDAO roleDAO, UserDAO userDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleDAO = roleDAO;
        this.userDAO = userDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void createTablesAndData() {
        createTableUsers();
        createTableRoles();
        createTableRolesUsers();
        insertRoles();
        insertUsers();
    }

    private void createTableUsers() {
        em.createNativeQuery("CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(50) NOT NULL, " +
                "email VARCHAR(50) NOT NULL, " +
                "password VARCHAR(200) NOT NULL" +
                ") ENGINE=MyISAM").executeUpdate();
    }

    private void createTableRoles() {
        em.createNativeQuery("CREATE TABLE IF NOT EXISTS role (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL" +
                ") ENGINE=MyISAM").executeUpdate();
    }

    private void createTableRolesUsers() {
        em.createNativeQuery("CREATE TABLE IF NOT EXISTS users_roles (" +
                "user_id BIGINT NOT NULL, " +
                "roles_id BIGINT NOT NULL, " +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (roles_id) REFERENCES role(id) ON DELETE CASCADE, " +
                "PRIMARY KEY (user_id, roles_id)" +
                ") ENGINE=MyISAM").executeUpdate();
    }

    private void insertRoles() {
        if (roleDAO.getRoles().isEmpty()) {
            Role admin = new Role();
            admin.setName("ROLE_ADMIN");

            Role user = new Role();
            user.setName("ROLE_USER");

            roleDAO.saveRole(admin);
            roleDAO.saveRole(user);
        }
    }

    private void insertUsers() {
        if (userDAO.getUsers().isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
            admin.setEmail("admin@mail.ru");
            admin.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));

            User user = new User();
            user.setUsername("user");
            user.setPassword(bCryptPasswordEncoder.encode("user"));
            user.setEmail("user@mail.ru");
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));

            userDAO.saveUser(admin);
            userDAO.saveUser(user);
        }
    }
}