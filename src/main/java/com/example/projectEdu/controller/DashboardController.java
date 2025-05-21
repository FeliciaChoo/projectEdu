package com.example.projectEdu.controller;

import com.example.projectEdu.model.Project;
import com.example.projectEdu.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final ProjectService projectService;

    public DashboardController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/student-dashboard")
    public String showDashboard(Model model) {

        Long studentId = 1L;

        model.addAttribute("totalProjects", projectService.countProjectsByStudentId(studentId));
        model.addAttribute("completedProjects", projectService.countCompletedProjectsByStudentId(studentId));
        model.addAttribute("activeProjects", projectService.countActiveProjectsByStudentId(studentId));
        model.addAttribute("projectList", projectService.findByStudentStudentId(studentId));
        model.addAttribute("content", "fragments/student-dashboard");

        return "layout";
    }
}
