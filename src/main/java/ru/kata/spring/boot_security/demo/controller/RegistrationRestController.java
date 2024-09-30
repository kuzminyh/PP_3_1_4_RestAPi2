package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class RegistrationRestController {


    private final UserService userService;

    public RegistrationRestController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/registration")
    public ResponseEntity<String> addUser(@RequestBody User userForm, @RequestParam String passwordConfirmation) {
        if (userForm.getUsername() == null || userForm.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("Имя пользователя не может быть пустым");
        }
        if (userForm.getEmail() == null || userForm.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email не может быть пустым");
        }
        if (userForm.getPassword() == null || userForm.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Пароль не может быть пустым");
        }
        if (!userForm.getPassword().equals(passwordConfirmation)) {
            return ResponseEntity.badRequest().body("Пароль и его подтверждение не совпадают");
        }

        userService.saveUser(userForm);
        return ResponseEntity.ok("Пользователь успешно создан");
    }
}