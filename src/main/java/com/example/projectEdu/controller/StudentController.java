package com.example.projectEdu.controller;
import com.example.projectEdu.model.Funder;
import com.example.projectEdu.model.Project;
import com.example.projectEdu.model.Student;
import com.example.projectEdu.repository.ProjectRepository;
import com.example.projectEdu.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final ProjectService projectService;

    private final ProjectRepository projectRepository;
    private final StudentService studentService;
    private final FundService fundService;

    private final FunderService funderService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public StudentController(ProjectService projectService, ProjectRepository projectRepository, StudentService studentService, FundService fundService, FunderService funderService) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
        this.studentService = studentService;
        this.fundService = fundService;
        this.funderService = funderService;
    }


    @GetMapping("/student-dashboard/{id}")
    public String showStudentDashboard(@PathVariable("id") Long id, Model model, Authentication authentication) {
        Student student = studentService.findById(id).orElse(null);

        if (student == null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("id", userDetails.getId());
            return "user-not-found";
        }

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

    @GetMapping("/funder-dashboard/{id}")
    public String showFunderDashboard(@PathVariable Long id, Model model, Authentication authentication) {
        Funder funder = funderService.findById(id).orElse(null);

        if (funder == null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("id", userDetails.getId());
            return "user-not-found";
        }

        model.addAttribute("funder",               funderService.findById(id).orElse(null));
        model.addAttribute("fund",                 fundService.findByFunderId(id));
        model.addAttribute("totalProjectsFunded",  fundService.countByFunderId(id));
        model.addAttribute("totalAmountFunded",    fundService.sumByFunderId(id));
        model.addAttribute("completedProjects",    projectService.countCompletedProjectsByFunderId(id));
        model.addAttribute("title",                "Funder Dashboard");
        model.addAttribute("content",              "fragments/funder-dashboard");
        return "layout";
    }

    @GetMapping("/delete-project/{id}")
    public String showDeleteProject(@PathVariable("id") long projectId, Model model, @AuthenticationPrincipal CustomUserDetails userDetails){
        Project project = projectService.findById(projectId).orElse(null);

        if (!project.getStudent().getId().equals(userDetails.getId())) {
            return "redirect:/access-denied";
        }

        if (project.getCurrentAmount().compareTo(BigDecimal.ZERO) > 0) {
            return "redirect:/delete-not-allowed";
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

        if(project.getEndDate().isBefore(LocalDate.now())) {
            return "redirect:/edit-not-allowed";
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

    @GetMapping("/apply")
    public String showApplicationForm(Model model, HttpServletRequest request) {
        if (!model.containsAttribute("project")) {
            Project project = new Project();
            project.setStudent(new Student());
            model.addAttribute("project", project);
        }

        model.addAttribute("title", "Apply for Funding");
        model.addAttribute("content", "fragments/apply");
        model.addAttribute("currentUri", request.getRequestURI());
        return "layout";
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
        return "redirect:/student/apply";
    }
}