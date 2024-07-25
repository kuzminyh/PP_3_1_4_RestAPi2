package ru.kata.spring.boot_security.demo.Service;

import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DAO.UserDAO;
import ru.kata.spring.boot_security.demo.Model.User;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserDAO userDAO;

    private final BCryptPasswordEncoder encoder;


    public UserService(UserDAO userDAO, BCryptPasswordEncoder encoder) {
        this.userDAO = userDAO;
        this.encoder = encoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        Hibernate.initialize(user.getRoles());
        return user;
    }

    public void saveUser(User user) {
        if (user.getId() == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            userDAO.saveUser(user);
        } else {
            User existingUser = userDAO.findUserById(user.getId());
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(existingUser.getRoles());
            userDAO.updateUser(user);
        }
    }

    public User findUserById(Long userId) {
        User user = userDAO.findUserById(userId);
        Hibernate.initialize(user.getRoles());
        return user;
    }

    public List<User> allUsers() {
        return userDAO.getUsers();
    }

    public void deleteUser(Long userId) {
        userDAO.deleteUser(userId);
    }

    public List<User> usergtList(Long idMin) {
        return userDAO.usergtList(idMin);
    }
}
