package ru.kata.spring.boot_security.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.Model.User;
import ru.kata.spring.boot_security.demo.Service.UserService;

import java.security.Principal;

@Controller
public class NewsController {

    @Autowired
    private UserService userService;

    @GetMapping("/news")
    public String news(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User currentUser = (User) userService.loadUserByUsername(username);
            model.addAttribute("currentUser", currentUser);
        }
        return "news";
    }
}