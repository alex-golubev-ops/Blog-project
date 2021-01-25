package com.leverx.blog.controller;

import com.leverx.blog.entity.Role;
import com.leverx.blog.entity.User;
import com.leverx.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        if (!userService.save(user)) {
            model.addAttribute("emailmessage", "email is exist");
        }
        return "redirect:/login";
    }
}
