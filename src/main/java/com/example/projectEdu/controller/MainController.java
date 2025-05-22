package com.example.projectEdu.controller;
import com.example.projectEdu.model.Student;
import com.example.projectEdu.service.ProjectService;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class MainController {

    private final ProjectRepository projectRepository;
   ;

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



    @PostMapping("/payment-success")
    public String showPaymentSuccess(Model model, HttpServletRequest request) {
        return "payment-success";
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
                                    @RequestParam(value = "otherCategoryInput", required = false) String otherCategoryInput,
                                    @RequestParam("imageFile") MultipartFile imageFile,
                                    Model model, RedirectAttributes redirectAttributes, @RequestParam(required = false) String otherCategory) {

        // Handle 'Others' category input
        if (otherCategory != null && !otherCategory.trim().isEmpty()) {
            project.setCategory(otherCategory.trim());
        }

        // Set default status if not set
        if (project.getStatus() == null || project.getStatus().isBlank()) {
            project.setStatus("Active");  // or "Upcoming", whichever you prefer
        }
        // Set default status if not set
        if (project.getStatus() == null || project.getStatus().isBlank()) {
            project.setStatus("Active");
        }
        if (project.getEndDate() == null) {
            project.setEndDate(LocalDate.now().plusMonths(3));
        }

        // Now validate
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);

            // Return form view (no save on error)
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

                    // Alternative filename handling without StringUtils
                    String originalFilename = imageFile.getOriginalFilename();
                    String safeFilename = originalFilename != null ?
                            originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_") :
                            "uploaded_file";

                    String filename = "upload_" + System.currentTimeMillis() + "_" + safeFilename;
                    imageFile.transferTo(uploadPath.resolve(filename));
                    project.setImageUrl("/uploads/" + filename);

                } catch (IOException e) {
                    e.printStackTrace();
                    // You might want to add error handling here, e.g. add error message to model
                }
            }
            System.out.println("Saving project: " + project.getTitle());
            projectRepository.save(project);

            // Add flash attribute for success message
            redirectAttributes.addFlashAttribute("successMessage", "Successfully submitted!");

            // Redirect to apply page or another page to avoid resubmission
            return "redirect:/apply";

        }




}
