package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final UserServiceImpl userServiceImpl;
    private final RoleServiceImpl roleServiceImpl;

    public AdminController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }


    @GetMapping("/admin/users")
    public String userList(Model model) {
        model.addAttribute("allUsers", userServiceImpl.allUsers());
        return "admin";
    }

    @GetMapping("/admin/users/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") User userForm,
                           @RequestParam("passwordConfirmation") String passwordConfirmation,
                           @RequestParam(value = "roleIds", required = false) Set<Long> roleIds,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "user-form";
        }
        if (!userForm.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "user-form";
        }
        if (roleIds == null || roleIds.isEmpty()) {
            model.addAttribute("roleError", "Необходимо выбрать хотя бы одну роль");
            return "user-form";
        }

        Set<Role> roles = roleIds.stream()
                .map(roleServiceImpl::findRoleById)
                .collect(Collectors.toSet());
        userForm.setRoles(roles);

        userServiceImpl.saveUser(userForm);
        return "redirect:/admin/users";
    }

    @GetMapping("/updateUser")
    public String showUpdateForm(@RequestParam("userId") long id, Model model) {
        User user = userServiceImpl.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleServiceImpl.getRoles());
        return "user-update-form";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User userForm, @RequestParam(value = "roleIds", required = false) Set<Long> roleIds,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user-update-form";
        }

        if (roleIds == null || roleIds.isEmpty()) {
            model.addAttribute("roleError", "Необходимо выбрать хотя бы одну роль");
            return "user-update-form";
        }


        if (userForm.getPassword().isEmpty()) {
            User existingUser = userServiceImpl.findUserById(userForm.getId());
            userForm.setPassword(existingUser.getPassword());
        }
        userServiceImpl.updateUser(userForm);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        if (action.equals("delete")) {
         userServiceImpl.deleteUser(userId);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userServiceImpl.usergtList(userId));
        return "admin";
    }

    @PostMapping("/admin/assignAdminRole")
    public String assignAdminRole(@RequestParam("userId") Long userId) {
        User user = userServiceImpl.findUserById(userId);

        boolean hasAdminRole = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (hasAdminRole) {
            return "redirect:/admin/users";
        }
        user.getRoles().add(new Role(1L));
        userServiceImpl.saveUser(user);

        return "redirect:/admin/users";
    }
}
