package ru.kata.spring.boot_security.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Model.Role;
import ru.kata.spring.boot_security.demo.Model.User;
import ru.kata.spring.boot_security.demo.Service.UserService;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/admin/users")
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
    public String saveUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "user-form";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirmation())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "user-form";
        }
        userService.saveUser(userForm);
        return "redirect:/admin/users";
    }

    @GetMapping("/updateUser")
    public String showUpdateForm(@RequestParam("userId") long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user-form";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam("passwordConfirmation") String passwordConfirmation,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user-form";
        }
        if (!user.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "user-form";
        }
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

    @PostMapping("/admin/assignAdminRole")
    public String assignAdminRole(@RequestParam("userId") Long userId) {
        User user = userService.findUserById(userId);

        boolean hasAdminRole = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (hasAdminRole) {
            return "redirect:/admin";
        }
        user.getRoles().add(new Role(2L));
        userService.saveUser(user);

        return "redirect:/admin";
    }
}
