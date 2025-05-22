package com.example.projectEdu.controller;

import com.example.projectEdu.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

        var funder = funderService.findById(id).orElse(null);
        model.addAttribute("funder", funderService.findById(id).orElse(null));
        System.out.println("Funder: " + funder);

        var fund = fundService.findByFunderId(id);
        model.addAttribute("fund", fundService.findByFunderId(id));
        System.out.println("Fund: " + fund);
        model.addAttribute("totalProjectsFunded", fundService.countByFunderId(id));
        model.addAttribute("totalAmountFunded", fundService.sumByFunderId(id));
        model.addAttribute("content", "fragments/funder-dashboard");

        return "layout";
    }

    @GetMapping("/donor")
    public String showDonationForm(Model model) {
        Long id = 1L;

        model.addAttribute("funder", funderService.findById(id).orElse(null));
        model.addAttribute("project", projectService.findById(id).orElse(null));
        model.addAttribute("content", "fragments/donor");

        return "layout";
    }
}
