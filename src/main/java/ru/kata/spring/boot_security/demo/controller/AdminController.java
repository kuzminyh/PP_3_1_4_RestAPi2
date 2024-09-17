package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/users")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin/users/new")
    public String saveUser(@ModelAttribute("user") User userForm,
                           @RequestParam("passwordConfirmation") String passwordConfirmation,
                           @RequestParam(value = "roleIds", required = false) Set<Long> roleIds,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.getRoles());
            return "user-form";
        }
        if (!userForm.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            model.addAttribute("allRoles", roleService.getRoles());
            return "user-form";
        }
        if (roleIds == null || roleIds.isEmpty()) {
            model.addAttribute("roleError", "Необходимо выбрать хотя бы одну роль");
            model.addAttribute("allRoles", roleService.getRoles());
            return "user-form";
        }

        Set<Role> roles = roleService.findRolesByIds(roleIds);
        userForm.setRoles(roles);
        userService.saveUser(userForm);
        return "redirect:/admin/users";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User userForm,
                             @RequestParam(value = "roleIds", required = false) Set<Long> roleIds,
                             BindingResult bindingResult, Model model) {

        // Проверка на наличие ошибок
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.getRoles());
            return "admin";
        }

        // Проверка на роли
        if (roleIds == null || roleIds.isEmpty()) {
            model.addAttribute("roleError", "Необходимо выбрать хотя бы одну роль");
            return "admin";
        }

        // Устанавливаем роли
        Set<Role> roles = roleService.findRolesByIds(roleIds);
        userForm.setRoles(roles);

        // Вызываем метод обновления в сервисе
        userService.updateUser(userForm);

        return "redirect:/admin/users";
    }



    @PostMapping("/admin/users")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        if (action.equals("delete")) {
            userService.deleteUser(userId);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User userForm,
                               @RequestParam("passwordConfirmation") String passwordConfirmation,
                               @RequestParam(value = "roleIds", required = false) Set<Long> roleIds,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin";
        }
        if (!userForm.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "admin";
        }
        if (roleIds == null || roleIds.isEmpty()) {
            model.addAttribute("roleError", "Необходимо выбрать хотя бы одну роль");
            return "admin";
        }
        Set<Role> roles = roleService.findRolesByIds(roleIds);
        userForm.setRoles(roles);
        userService.saveUser(userForm);
        return "redirect:/admin/users";
    }

    @GetMapping("/getUser")
    @ResponseBody
    public User getUser(@RequestParam("userId") long userId) {
        User user = userService.findUserById(userId);
        System.out.println("Возвращаемые данные пользователя: " + user); // Для отладки
        if (user != null) {
            return user;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }


}