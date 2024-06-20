package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
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

    @GetMapping("/admin/users/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("password") String password,
                           @RequestParam("passwordConfirmation") String passwordConfirmation,
                           Model model) {
        if (!password.equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "user-form";
        }
        if (user.getId() != null) {
            User existingUser = userService.findUserById(user.getId());
            if (existingUser != null) {
                if (password != null && !password.isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(password));
                }
                existingUser.setUsername(user.getUsername());
                existingUser.setEmail(user.getEmail());
                userService.saveUser(existingUser);
            } else {
                model.addAttribute("userError", "Пользователь не найден");
                return "user-form";
            }
        } else {
            if (password != null && !password.isEmpty()) {
                user.setPassword(passwordEncoder.encode(password));
            }
            userService.saveUser(user);
        }
        return "redirect:/admin";
    }

    @GetMapping("/updateUser")
    public String showUpdateForm(@RequestParam("userId") long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user-form";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam("password") String password,
                             @RequestParam("passwordConfirmation") String passwordConfirmation,
                             Model model) {
        if (!password.equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "user-form"; // Возврат к форме, если пароли не совпадают
        }

        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        } else {
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
        if ("delete".equals(action)) {
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

    @PostMapping("/admin/assignAdminRole")
    public String assignAdminRole(@RequestParam("userId") Long userId) {
        User user = userService.findUserById(userId);
        boolean hasAdminRole = user.getRoles().stream()
                .anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));
        if (hasAdminRole) {
            return "redirect:/admin";
        }
        Role adminRole = new Role(2L);
        user.getRoles().add(adminRole);
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
