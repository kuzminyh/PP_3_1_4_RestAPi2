package ru.kata.spring.boot_security.demo.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.exception.UsernameSuchNotFoundException;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class  UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    @Transactional
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) { return false; }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean updateUser(User user) {
        userRepository.save(user);
        return true;
    }

    public User getUser(int id) {
        Optional<User> userFromDB = userRepository.findById(id);
        return userFromDB.orElseThrow(() -> new UsernameSuchNotFoundException("User с этим id не найден"))  ;
            //    orElse(new User());
    }

    @Transactional
    public boolean deleteUser(int id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


}