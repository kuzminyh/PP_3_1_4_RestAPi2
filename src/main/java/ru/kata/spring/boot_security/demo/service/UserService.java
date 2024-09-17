package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User loadUserByUsername(String username);

    void saveUser(User user);

    User findUserById(Long userId);

    List<User> allUsers();

    void deleteUser(Long userId);

    List<User> usergtList(Long idMin);

    void updateUser(User user);
}
