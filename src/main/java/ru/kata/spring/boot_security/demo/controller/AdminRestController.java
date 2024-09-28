package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/admin/users")
    public ResponseEntity<String> createUser(@RequestBody User userForm, @RequestParam String passwordConfirmation) {
        if (userForm.getUsername() == null || userForm.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("Имя пользователя не может быть пустым");
        }
        if (userForm.getEmail() == null || userForm.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email не может быть пустым");
        }
        if (userForm.getPassword() == null || userForm.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Пароль не может быть пустым");
        }
        if (userForm.getRoles() == null || userForm.getRoles().isEmpty()) {
            return ResponseEntity.badRequest().body("Необходимо выбрать хотя бы одну роль");
        }
        if (!userForm.getPassword().equals(passwordConfirmation)) {
            return ResponseEntity.badRequest().body("Пароль и его подтверждение не совпадают");
        }

        userService.saveUser(userForm);
        return ResponseEntity.ok("Пользователь успешно создан");
    }

//    @PathVariable("id") long userId,
    @PutMapping("/admin/users/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User userForm) {
        if (userForm.getRoles() == null || userForm.getRoles().isEmpty()) {
            return ResponseEntity.badRequest().body("Необходимо выбрать хотя бы одну роль");
        }

        if (userForm.getUsername() == null || userForm.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("Имя пользователя не может быть пустым");
        }

        if (userForm.getEmail() == null || userForm.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email не может быть пустым");
        }
        userService.updateUser(userForm);
        return ResponseEntity.ok("Пользователь упешно обновлен");
    }



    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Пользователь успешно удалён");
    }
    @GetMapping("/admin/getRoles")
    public List<String> getRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
    @GetMapping("/admin/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}