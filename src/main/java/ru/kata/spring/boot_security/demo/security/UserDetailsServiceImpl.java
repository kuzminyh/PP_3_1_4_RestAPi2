package ru.kata.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.exception.UsernameSuchNotFoundException;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameSuchNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) { throw new UsernameSuchNotFoundException("User not found"); }
        return user;
    }
}
