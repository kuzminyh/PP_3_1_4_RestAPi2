package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @GetMapping("/users/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("password") String password) {
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/updateUser")
    public String showUpdateForm(@RequestParam("userId") long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user-form";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user, @RequestParam("password") String password) {
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        } else {
            // Загружаем текущего пользователя из базы данных, чтобы сохранить старый пароль
            User existingUser = userService.findUserById(user.getId());
            user.setPassword(existingUser.getPassword());
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        if (action.equals("delete")) {
            boolean result = userService.deleteUser(userId);
            if (result) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("Failed to delete user. User not found.");
            }
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }
}