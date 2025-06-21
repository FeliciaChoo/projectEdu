package com.example.projectEdu.controller;
import com.example.projectEdu.model.Funder;
import com.example.projectEdu.model.Student;
import com.example.projectEdu.repository.FunderRepository;
import com.example.projectEdu.repository.StudentRepository;
import com.example.projectEdu.service.CustomUserDetails;
import com.example.projectEdu.service.FunderService;
import com.example.projectEdu.service.ProjectService;
import com.example.projectEdu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.security.Principal;
import com.example.projectEdu.model.Project;
import com.example.projectEdu.repository.ProjectRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Validator;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class MainController {

    private final ProjectRepository projectRepository;
    private final StudentService studentService;
    private final FunderService funderService;

    private final ProjectService projectService;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final FunderRepository funderRepository;

    // Constructor injection
    public MainController(ProjectRepository projectRepository, ProjectService projectService,
                          StudentService studentService,
                          FunderService funderService, PasswordEncoder passwordEncoder, StudentRepository studentRepository, FunderRepository funderRepository) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
        this.studentService = studentService;
        this.funderService = funderService;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.funderRepository = funderRepository;
    }
    @GetMapping("/test-student-service")
    public String testStudentService() {
        // Test with a known ID or use 1L as a test case
        Optional<Student> student = studentService.findById(1L);
        System.out.println("Student found: " + student.orElse(null));
        return "redirect:/";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam("userType") String userType,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam Map<String, String> params,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        // Validate passwords
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords do not match.");
            return "redirect:/register";
        }

        // Check if email already exists
        boolean emailExists = studentRepository.findByEmail(email).isPresent()
                || funderRepository.findByEmail(email).isPresent();
        if (emailExists) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email already registered.");
            return "redirect:/register";
        }

        // Handle image upload
        String profileImageUrl = "";
        if (!imageFile.isEmpty()) {
            try {
                String uploadDir = "uploads/profile/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = imageFile.getOriginalFilename();
                String safeFilename = originalFilename != null ?
                        originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_") :
                        "profile_img";

                String filename = "profile_" + System.currentTimeMillis() + "_" + safeFilename;
                imageFile.transferTo(uploadPath.resolve(filename));
                profileImageUrl = "/uploads/profile/" + filename;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Create and save student or funder
        if ("student".equalsIgnoreCase(userType)) {
            Student student = new Student();
            student.setName(Arrays.stream(params.get("fullNameStudent").toLowerCase().split("\\s+"))
                    .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                    .collect(Collectors.joining(" ")));
            student.setEmail(email.toLowerCase());
            student.setPassword(passwordEncoder.encode(password));
            student.setUniversity("Other".equals(params.get("university")) ?
                    params.get("otherUniversity") : params.get("university"));
            student.setProfileUrl(profileImageUrl);

            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("successMessage", "Student registration successful! Please login.");
            return "redirect:/login";

        } else if ("funder".equalsIgnoreCase(userType)) {
            Funder funder = new Funder();
            funder.setName(Arrays.stream(params.get("fullNameFunder").toLowerCase().split("\\s+"))
                    .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                    .collect(Collectors.joining(" ")));
            funder.setEmail(email.toLowerCase());
            funder.setPassword(passwordEncoder.encode(password));
            funder.setProfileUrl(profileImageUrl);

            funderService.saveFunder(funder);
            redirectAttributes.addFlashAttribute("successMessage", "Funder registration successful! Please login.");
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Invalid user type.");
        return "redirect:/register";
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {

        model.addAttribute("totalProjects", projectService.totalProjects());
        model.addAttribute("totalFunders",  funderService.totalFunders());
        model.addAttribute("totalStudents", studentService.totalStudents());
        model.addAttribute("title", "Home");
        model.addAttribute("content", "fragments/homeContent");
        // Add current URI to model for active link highlight
        model.addAttribute("currentUri", request.getRequestURI());
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

    @PostMapping("/logout")
    public String userLogout() {
        return "redirect:/login?logout";
    }

    @GetMapping("/project-ended")
    public String showProjectEnded() {
        return "project-ended";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }




}