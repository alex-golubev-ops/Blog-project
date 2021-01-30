package com.leverx.blog.controller;

import com.leverx.blog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth/forgot_password")
    public String forgotPassword(Model model) {
        model.addAttribute("begin", true);
        return "forgotPassword";
    }

    @PostMapping("auth/forgot_password")
    public String getEmail(@RequestParam String email, Model model) {
        if (!userService.forgotPassword(email)) {
            model.addAttribute("message", "User is not exist");
            model.addAttribute("begin", true);
        } else {
            model.addAttribute("code", true);
            model.addAttribute("email", email);
        }
        return "forgotPassword";
    }

    @GetMapping("/auth/check_code")
    public String checkCode(@RequestParam String code, @RequestParam String email, Model model) {
        if (userService.checkCode(code, email)) {
            model.addAttribute("newpassword", true);
            model.addAttribute("codefromredis",code);
        } else {
            model.addAttribute("message", "Code is wrong\nPlease repeat");
            model.addAttribute("begin", true);
        }
        return "forgotPassword";
    }

    @PostMapping("/auth/reset")
    public String resetPassword(@RequestParam String code, @RequestParam String password, Model model) {
        if(!userService.resetPassword(code,password)){
            model.addAttribute("message", "Error\nPlease repeat please!");
            model.addAttribute("begin", true);
        }
        return "redirect:/login";
    }
}
