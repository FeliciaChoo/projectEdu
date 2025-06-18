package com.example.projectEdu.controller;

import com.example.projectEdu.model.Funder;
import com.example.projectEdu.model.Project;
import com.example.projectEdu.model.Student;
import com.example.projectEdu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StudentService studentService;
    private final FunderService funderService;
    private final ProjectService projectService;
    private final FundService fundService;

    @Autowired
    public AdminController(
            StudentService studentService,
            FunderService funderService,
            ProjectService projectService,
            FundService fundService
    ) {
        this.studentService = studentService;
        this.funderService  = funderService;
        this.projectService = projectService;
        this.fundService    = fundService;
    }

    @GetMapping("/admin-dashboard")
    public String showAdminDashboard(Model model) {
        model.addAttribute("totalProjects", projectService.totalProjects());
        model.addAttribute("totalFunders",  funderService.totalFunders());
        model.addAttribute("totalStudents", studentService.totalStudents());
        model.addAttribute("students",      studentService.findAll());
        model.addAttribute("funders",       funderService.findAll());
        model.addAttribute("projects",      projectService.findAll());

        model.addAttribute("title",   "Admin Dashboard");
        model.addAttribute("content", "fragments/admin-dashboard");
        return "layout";
    }

    @PostMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes ra) {
        studentService.findById(id).ifPresent(studentService::deleteStudent);
        ra.addFlashAttribute("successMessage", "Student deleted successfully!");
        return "redirect:/admin/admin-dashboard";
    }

    @PostMapping("/delete-funder/{id}")
    public String deleteFunder(@PathVariable Long id, RedirectAttributes ra) {
        funderService.findById(id).ifPresent(funderService::deleteFunder);
        ra.addFlashAttribute("successMessage", "Funder deleted successfully!");
        return "redirect:/admin/admin-dashboard";
    }

    @PostMapping("/delete-project/{id}")
    public String deleteProject(@PathVariable Long id, RedirectAttributes ra) {
        projectService.findById(id).ifPresent(projectService::deleteProject);
        ra.addFlashAttribute("successMessage", "Project deleted successfully!");
        return "redirect:/admin/admin-dashboard";
    }

    @GetMapping("/funder-dashboard/{id}")
    public String showFunderDashboard(@PathVariable Long id, Model model) {
        model.addAttribute("funder",               funderService.findById(id).orElse(null));
        model.addAttribute("fund",                 fundService.findByFunderId(id));
        model.addAttribute("totalProjectsFunded",  fundService.countByFunderId(id));
        model.addAttribute("totalAmountFunded",    fundService.sumByFunderId(id));
        model.addAttribute("completedProjects",    projectService.countCompletedProjectsByFunderId(id));
        model.addAttribute("title",                "Funder Dashboard");
        model.addAttribute("content",              "fragments/funder-dashboard");
        return "layout";
    }

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
}
