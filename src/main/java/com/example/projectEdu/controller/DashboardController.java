package com.example.projectEdu.controller;

import com.example.projectEdu.model.Fund;
import com.example.projectEdu.model.Project;
import com.example.projectEdu.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {

    private final ProjectService projectService;
    private final StudentService studentService;
    private final FunderService funderService;
    private final FundService fundService;

    public DashboardController(ProjectService projectService, StudentService studentService, FunderService funderService, FundService fundService) {
        this.projectService = projectService;
        this.studentService = studentService;
        this.funderService = funderService;
        this.fundService = fundService;
    }

    @GetMapping("/student-dashboard")
    public String showStudentDashboard(Model model) {

        Long id = 1L;

        model.addAttribute("student", studentService.findById(id).orElse(null));
        model.addAttribute("totalProjects", projectService.countByStudentId(id));
        model.addAttribute("completedProjects", projectService.countByStudentIdAndStatus(id, "Completed"));
        model.addAttribute("activeProjects", projectService.countByStudentIdAndStatus(id, "Active"));
        model.addAttribute("projects", projectService.findByStudentId(id));
        model.addAttribute("content", "fragments/student-dashboard");

        return "layout";
    }

    @GetMapping("/funder-dashboard")
    public String showFunderDashboard(Model model) {
        Long id = 1L;

        model.addAttribute("funder", funderService.findById(id).orElse(null));
        model.addAttribute("fund", fundService.findByFunderId(id));
        model.addAttribute("totalProjectsFunded", fundService.countByFunderId(id));
        model.addAttribute("totalAmountFunded", fundService.sumByFunderId(id));
        model.addAttribute("content", "fragments/funder-dashboard");

        return "layout";
    }

    @GetMapping("/donor/{id}")
    public String showDonationForm(@PathVariable("id") Long projectId, Model model) {
        Long id = 1L;

        var funder = funderService.findById(id).orElse(null);
        var project = projectService.findById(projectId).orElse(null);

        System.out.println("Funder: " + funder);   // print funder object or null
        System.out.println("Project: " + project); // print project object or null

        model.addAttribute("funder", funderService.findById(id).orElse(null));
        model.addAttribute("project", projectService.findById(projectId).orElse(null));
        model.addAttribute("fund", new Fund());
        model.addAttribute("content", "fragments/donor");

        return "layout";
    }

    @GetMapping("/project/delete/{id}")
    public String deleteProject(@PathVariable("id") Long projectId, Model model) {
        Project project = projectService.findById(projectId).
                orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + projectId));
        projectService.deleteProject(project);
        return "redirect:/student-dashboard";
    }

    @PostMapping("/fund/{id}")
    public String addNewFund(@Valid Fund fund, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/donor/{projectId}";
        }
        return "redirect:/payment-success";
    }

    @PostMapping("/payment-success")
    public String processFund() {
        return "payment-success";
    }


}
