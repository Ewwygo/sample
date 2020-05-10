package com.netcracker.denisik.controllers;

import com.netcracker.denisik.entity.Role;
import com.netcracker.denisik.userDetails.UserServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SignUpController {

    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;

    @Autowired
    public SignUpController(PasswordEncoder passwordEncoder, UserServiceImpl userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/signUp")
    public String signUp(){
        return "registration";
    }

    @PostMapping("/signUp")
    public String signUp(@RequestParam String login, @RequestParam String password, @RequestParam Role role) {
        String hashPassword = passwordEncoder.encode(password);
        try {
            userService.saveUser(login,password,role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/hello";
    }
}
