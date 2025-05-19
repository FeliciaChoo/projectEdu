package com.example.projectEdu.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.projectEdu.model.Project;
import com.example.projectEdu.repository.ProjectRepository;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home");
        model.addAttribute("content", "index");
        return "layout";
    }

    @GetMapping("/donation-form")
    public String donationForm(Model model) {
        model.addAttribute("title", "Project Funding Application");
        model.addAttribute("content", "donationForm");
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

    @GetMapping("/application")
    public String applicationPage() {
        return "application";
    }
}