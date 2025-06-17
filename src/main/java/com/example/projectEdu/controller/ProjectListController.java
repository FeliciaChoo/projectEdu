package com.example.projectEdu.controller;

import com.example.projectEdu.model.Project;
import com.example.projectEdu.service.FundService;
import com.example.projectEdu.service.ProjectServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class ProjectListController {

    private final ProjectServiceImpl projectService;
    private final FundService fundService;

    public ProjectListController(ProjectServiceImpl projectService, FundService fundService) {
        this.projectService = projectService;
        this.fundService = fundService;
    }

    @GetMapping("/project/{id}")
    public String getProjectById(@PathVariable("id") Long projectId, Model model) {
        System.out.println("Project controller called with id = " + projectId);
        Optional<Project> optionalProject = projectService.findById(projectId);
        if (optionalProject == null) {
            return "error/404";
        }


        Project project = optionalProject.get();
        Integer backerCount = fundService.countByProjectId(projectId);

        long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), project.getEndDate());
        if (daysLeft < 0) daysLeft = 0;

        model.addAttribute("project", project);
        model.addAttribute("projectId", projectId);
        model.addAttribute("backerCount", backerCount);
        model.addAttribute("daysLeft", daysLeft);
        model.addAttribute("content", "fragments/project");  // Tell layout which fragment to use
        model.addAttribute("title", project.getTitle());    // Optional: set page title
        return "layout";  // Return your main Thymeleaf template
    }


    @GetMapping("/projects")
    public String listProjects(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            Model model) {

        List<Project> projects;

        if (status != null && !status.isEmpty() && !"all".equalsIgnoreCase(status)) {
            if (status.equalsIgnoreCase("upcoming")) {
                projects = projectService.getProjectsByStatus("Active");
            } else if (status.equalsIgnoreCase("successful")) {
                projects = projectService.getProjectsByStatus("Completed");
            } else {
                projects = projectService.getProjectsByStatus(status);
            }
        } else {
            projects = projectService.getAllProjects();
        }

        // Defensive check: ensure projects is never null
        if (projects == null) {
            projects = Collections.emptyList();
        }

        // Filter by category if provided
        if (category != null && !category.isEmpty()) {
            String categoryLower = category.toLowerCase();
            projects = projects.stream()
                    .filter(p -> p.getCategory() != null && p.getCategory().toLowerCase().equals(categoryLower))
                    .toList();
        }

        // Filter by search keyword in title or description if provided
        if (search != null && !search.isEmpty()) {
            String searchLower = search.toLowerCase();
            projects = projects.stream()
                    .filter(p -> (p.getTitle() != null && p.getTitle().toLowerCase().contains(searchLower)) ||
                            (p.getDescription() != null && p.getDescription().toLowerCase().contains(searchLower)))
                    .toList();
        }

        // Fetch and clean all categories for the dropdown
        List<String> categories = projectService.getAllCategories();
        if (categories == null) {
            categories = Collections.emptyList();
        }

        List<String> cleanedCategories = new ArrayList<>();
        for (String categoryEntry : categories) {
            String[] split = categoryEntry.split(",");
            for (String c : split) {
                c = c.trim();
                if (!c.equalsIgnoreCase("Other") && !cleanedCategories.contains(c)) {
                    cleanedCategories.add(c);
                }
            }
        }

        System.out.println("Loaded projects count: " + projects.size());
        projects.forEach(p -> System.out.println(p.getTitle()));

        // Add attributes to model for Thymeleaf view
        model.addAttribute("projects", projects);
        model.addAttribute("categories", cleanedCategories);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("searchTerm", search);
        model.addAttribute("activeTab", status != null ? status : "all");
        model.addAttribute("content", "fragments/project-list");

        return "layout";
    }
    @GetMapping("/projects/upcoming")
    public String showUpcomingProjects(Model model) {
        List<Project> upcoming = projectService.getProjectsByStatus("Upcoming");
        model.addAttribute("projects", upcoming);
        return "upcoming-projects";
    }

}
