package com.example.projectEdu.controller;

import com.example.projectEdu.model.Project;
import com.example.projectEdu.service.ProjectService;
import com.example.projectEdu.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final ProjectService projectService;
    private final StudentService studentService;

    public DashboardController(ProjectService projectService, StudentService studentService) {
        this.projectService = projectService;
        this.studentService = studentService;
    }

    @GetMapping("/student-dashboard")
    public String showDashboard(Model model) {

        Long id = 1L;

        model.addAttribute("student", studentService.findById(id).orElse(null));
        model.addAttribute("totalProjects", projectService.countProjectsByStudentId(id));
        model.addAttribute("completedProjects", projectService.countCompletedProjectsByStudentId(id));
        model.addAttribute("activeProjects", projectService.countActiveProjectsByStudentId(id));
        model.addAttribute("projects", projectService.findByStudentId(id));
        model.addAttribute("content", "fragments/student-dashboard");

        return "layout";
    }
}
