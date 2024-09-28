package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.UserDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserDAO userDAO;

    private final BCryptPasswordEncoder encoder;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserDAO userDAO, BCryptPasswordEncoder encoder, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.encoder = encoder;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        return user;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (user.getRoles() == null) {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.saveUser(user);
    }


    @Override
    @Transactional
    public User findUserById(Long userId) {
        User user = userDAO.findUserById(userId);
        return user;
    }

    @Override
    public List<User> allUsers() {
        return userDAO.getUsers();
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userDAO.deleteUser(userId);
    }

    @Override
    public List<User> usergtList(Long idMin) {
        return userDAO.usergtList(idMin);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User existingUser = userDAO.findUserById(user.getId());
            if (!user.getPassword().equals(existingUser.getPassword()) ) {
                user.setPassword(encoder.encode(user.getPassword()));
            } else {
                user.setPassword(existingUser.getPassword());
            }
        userDAO.updateUser(user);
    }
}