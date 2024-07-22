package ru.kata.spring.boot_security.demo.DAO;

import ru.kata.spring.boot_security.demo.Model.User;

import java.util.List;

public interface UserDAO {

    List<User> getUsers();

    void saveUser(User user);

    void updateUser(User user);

    User findUserById(long id);

    void deleteUser(long id);

    User findByUsername(String username);

    List<User> usergtList(Long idMin);

}
