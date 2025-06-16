package com.example.projectEdu.controller;
import com.example.projectEdu.model.Project;
import com.example.projectEdu.model.Student;
import com.example.projectEdu.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final ProjectService projectService;
    private final StudentService studentService;
    private final FundService fundService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public StudentController(ProjectService projectService, StudentService studentService, FundService fundService) {
        this.projectService = projectService;
        this.studentService = studentService;
        this.fundService = fundService;
    }

    //done
    @GetMapping("/student-dashboard/{id}")
    public String showStudentDashboard(@PathVariable("id") Long id, Model model, Authentication authentication) {
        Student student = studentService.findById(id).orElse(null);

        String loggedInUserEmail = ((CustomUserDetails) authentication.getPrincipal()).getEmail();
        boolean isOwner = loggedInUserEmail.equals(student.getEmail());

        model.addAttribute("student", student);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("totalProjects", projectService.countByStudentId(id));
        model.addAttribute("completedProjects", projectService.countByStudentIdAndStatus(id, "Completed"));
        model.addAttribute("activeProjects", projectService.countByStudentIdAndStatus(id, "Active"));
        model.addAttribute("fundRaised", fundService.sumByStudentId(id));
        model.addAttribute("projects", projectService.findByStudentId(id));
        model.addAttribute("title", "Student Dashboard");
        model.addAttribute("content", "fragments/student-dashboard");

        return "layout";
    }

    @GetMapping("/delete-project/{id}")
    public String showDeleteProject(@PathVariable("id") long projectId, Model model, @AuthenticationPrincipal CustomUserDetails userDetails){
        Project project = projectService.findById(projectId).orElse(null);

        if (!project.getStudent().getId().equals(userDetails.getId())) {
            return "redirect:/access-denied";
        }

        model.addAttribute("project", project);
        return "/delete-project";
    }

    @PostMapping("/delete/{id}")
    public String deleteProject(@PathVariable("id") Long projectId,
                                @RequestParam("password") String password,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        Project project = projectService.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + projectId));

        Student student = project.getStudent();

        if (!passwordEncoder.matches(password, student.getPassword())) {
            model.addAttribute("project", project);
            model.addAttribute("error", "Incorrect password.");
            return "/delete-project";
        }

        projectService.deleteProject(project);
        redirectAttributes.addFlashAttribute("success", "Project deleted successfully.");
        return "redirect:/student/student-dashboard/" + student.getId();
    }

    @GetMapping("/edit-project/{id}")
    public String showUpdateForm(@PathVariable("id") long projectId, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Project project = projectService.findById(projectId).orElseThrow(()-> new IllegalArgumentException("Invalid project Id:" + projectId));

        if (!project.getStudent().getId().equals(userDetails.getId())) {
            return "redirect:/access-denied";
        }

        model.addAttribute("project", project);
        model.addAttribute("title", "Edit Project");
        model.addAttribute("content", "fragments/edit-project");
        return "layout";
    }

    @PostMapping("/update/{id}")
    public String updateProject(@PathVariable("id") long projectId,
                                @Valid Project project,
                                BindingResult result,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                Model model,
                                Principal principal) {

        if (result.hasErrors()) {
            System.out.println("Validation errors:");
            result.getAllErrors().forEach(error -> System.out.println(error));
            model.addAttribute("content", "fragments/edit-project");
            model.addAttribute("project", project);
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

        CustomUserDetails userDetails = (CustomUserDetails) ((Authentication) principal).getPrincipal();
        Long id = userDetails.getId();

        project.setProjectId(projectId);
        projectService.updateProject(project);
        return "redirect:/student/student-dashboard/" + id;
    }
}