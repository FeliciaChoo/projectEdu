package com.example.projectEdu.controller;
import com.example.projectEdu.model.Student;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    private final ProjectRepository projectRepository;

    // Constructor injection
    public MainController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("title", "Home");
        model.addAttribute("content", "fragments/homeContent");
        // Add current URI to model for active link highlight
        model.addAttribute("currentUri", request.getRequestURI());
        return "layout";
    }

    @GetMapping("/apply")
    public String showApplicationForm(Model model, HttpServletRequest request) {
        Project project = new Project();
        project.setStudent(new Student());  // initialize nested Student object
        model.addAttribute("title", "Apply for Funding");
        model.addAttribute("content", "fragments/apply");
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("project", project);
        return "layout";
    }


    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("title", "Log In Page");
        model.addAttribute("content", "fragments/login");  // Example fragment for login form
        return "layout";  // returns layout.html
    }


    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("title", "Register Page");
        model.addAttribute("content", "fragments/register");
        return "layout"; // Your main layout template
    }


    @PostMapping("/apply")
    public String submitApplication(@Valid @ModelAttribute("project") Project project,
                                    BindingResult bindingResult,
                                    @RequestParam("imageFile") MultipartFile imageFile,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Apply for Funding");
            model.addAttribute("content", "fragments/apply");
            return "layout";
        }

        if (!imageFile.isEmpty()) {
            try {
                // Define a directory to save uploaded images (ensure it exists)
                String uploadDir = "src/main/resources/static/image/";
                // Generate a unique filename, e.g., using project title and timestamp
                String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path filepath = Paths.get(uploadDir, filename);

                // Create directories if they don't exist
                Files.createDirectories(filepath.getParent());

                // Save the file locally
                imageFile.transferTo(filepath.toFile());

                // Save the relative path or filename to the project entity
                project.setImageUrl("/image/" + filename);


            } catch (IOException e) {
                e.printStackTrace();
                // You might want to add error handling here, e.g. add error message to model
            }
        }

        project.setStatus("Upcoming"); // default status
        projectRepository.save(project);
        return "redirect:/projects";
    }


}