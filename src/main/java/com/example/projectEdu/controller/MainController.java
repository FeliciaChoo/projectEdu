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
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class MainController {

    private final ProjectRepository projectRepository;
    private final StudentService studentService;
    private final FunderService funderService;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final FunderRepository funderRepository;

    // Constructor injection
    public MainController(ProjectRepository projectRepository,
                          StudentService studentService,
                          FunderService funderService, PasswordEncoder passwordEncoder, StudentRepository studentRepository, FunderRepository funderRepository) {
        this.projectRepository = projectRepository;
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
        return "redirect:/"; // Or return a test view if preferred
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam Map<String, String> params, RedirectAttributes redirectAttributes, Model model) {
        System.out.println("Received params: " + params);

        String userType = params.get("userType");
        String email = params.get("email");
        String password = params.get("password");
        String confirmPassword = params.get("confirmPassword");

        if (userType == null || email == null || password == null) {
            redirectAttributes.addFlashAttribute("errorMessage", true);
            return "redirect:/register";
        }

        if (!password.equals(confirmPassword)){
            redirectAttributes.addAttribute("passwordError", true);
            return "redirect:/register";
        }

        Student existingStudent = studentRepository.findByEmail(email).orElse(null);
        Funder existingFunder = funderRepository.findByEmail(email).orElse(null);

        if (existingStudent != null || existingFunder != null) {
            redirectAttributes.addAttribute("emailError", true);
            return "redirect:/register";
        }

        if ("student".equalsIgnoreCase(userType)) {
            String fullNameStudent = params.get("fullNameStudent");
            String university = params.get("university");
            String otherUniversity = params.get("otherUniversity");

            Student student = new Student();
            student.setName(fullNameStudent);
            student.setEmail(email);
            student.setPassword(passwordEncoder.encode(password));
            student.setUniversity("Other".equals(university) ? otherUniversity : university);
            student.setProfileUrl("");
            try {
                studentService.saveStudent(student);
            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("errorMessage", "Save failed: " + e.getMessage());
                return "redirect:/register";
            }

            redirectAttributes.addFlashAttribute("successMessage", "Student registration successful! Please login.");
            return "redirect:/login";
        } else if ("funder".equalsIgnoreCase(userType)) {
            String fullNameFunder = params.get("fullNameFunder");

            Funder funder = new Funder();
            funder.setName(fullNameFunder);
            funder.setEmail(email);
            funder.setPassword(passwordEncoder.encode(password));
            funder.setProfileUrl("");
            funderService.saveFunder(funder);
            redirectAttributes.addFlashAttribute("successMessage", "Funder registration successful! Please login.");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid user type.");
            return "redirect:/register";
        }
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
                                    Model model,
                                    RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();


        Long studentId = userDetails.getId();

        Student student = studentService.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        project.setStudent(student);
        if (project.getStatus() == null || project.getStatus().isBlank()) {
            project.setStatus("Active");
        }
        if (project.getEndDate() == null) {
            project.setEndDate(LocalDate.now().plusMonths(3));
        }

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
            model.addAttribute("title", "Apply for Funding");
            model.addAttribute("content", "fragments/apply");
            return "layout";
        }



        if (!imageFile.isEmpty()) {
            try {
                String uploadDir = "uploads/";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = imageFile.getOriginalFilename();
                String safeFilename = originalFilename != null ?
                        originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_") :
                        "uploaded_file";

                String filename = "upload_" + System.currentTimeMillis() + "_" + safeFilename;
                imageFile.transferTo(uploadPath.resolve(filename));
                project.setImageUrl("/uploads/" + filename);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Saving project: " + project.getTitle());
        projectRepository.save(project);

        redirectAttributes.addFlashAttribute("successMessage", "Successfully submitted!");

        return "redirect:/projects";
    }

    @GetMapping("/logout")
    public String userLogout() {
        return "redirect:/login?logout";
    }






}