package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UserRestController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;

    public UserRestController(UserService userService, UserDetailsService userDetailsService) { this.userService = userService;
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("/userInfo")
    public User getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return (User) userDetailsService.loadUserByUsername(currentUsername);
    }

}
