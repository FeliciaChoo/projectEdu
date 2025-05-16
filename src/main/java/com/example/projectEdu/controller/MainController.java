package com.example.projectEdu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home");
        model.addAttribute("content", "index");
        return "layout";
    }

    @GetMapping("/apply")
    public String showApplicationForm(Model model) {
        model.addAttribute("title", "Apply for Funding");
        model.addAttribute("content", "application");
        return "layout";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "logIn";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "registration";
    }
}