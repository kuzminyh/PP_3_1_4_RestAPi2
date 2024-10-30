package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    boolean saveUser(User user);

    boolean updateUser(User user);

    User getUser(int id);

    boolean deleteUser(int id);

}
