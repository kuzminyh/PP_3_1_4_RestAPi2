package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserDAO userDAO;

    private final BCryptPasswordEncoder encoder;


    public UserServiceImpl(UserDAO userDAO, BCryptPasswordEncoder encoder) {
        this.userDAO = userDAO;
        this.encoder = encoder;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        Hibernate.initialize(user.getRoles());
        return user;
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        if(user.getRoles() == null){
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        }
            user.setPassword(encoder.encode(user.getPassword()));
            userDAO.saveUser(user);
    }


    @Transactional(readOnly = true)
    @Override
    public User findUserById(Long userId) {
        User user = userDAO.findUserById(userId);
        Hibernate.initialize(user.getRoles());
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> allUsers() {
        return userDAO.getUsers();
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userDAO.deleteUser(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> usergtList(Long idMin) {
        return userDAO.usergtList(idMin);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.updateUser(user);
    }
}
