package ru.kata.spring.boot_security.demo.Service;

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
        return userDAO.findByUsername(username);
    }

    public void saveUser(User user)  {
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.saveUser(user);
    }

    public User findUserById(Long userId) {
        return userDAO.findUserById(userId);
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