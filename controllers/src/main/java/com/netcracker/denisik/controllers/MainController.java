package com.netcracker.denisik.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/hello")
    public String hello(){
        return "index";
    }

    @GetMapping("/addEntity")
    public String addEntity(){
        return "admin/addEntity";
    }
}
