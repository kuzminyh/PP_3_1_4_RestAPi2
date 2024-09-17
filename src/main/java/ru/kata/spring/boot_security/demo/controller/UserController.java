package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String userHomePage(Model model, Principal principal) {
        // Получаем текущего пользователя по имени
        User currentUser = userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", currentUser);
        return "home"; // Название Thymeleaf шаблона для личного кабинета
    }
}
