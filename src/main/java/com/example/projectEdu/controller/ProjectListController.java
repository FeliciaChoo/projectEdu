package com.example.projectEdu.controller;

import com.example.projectEdu.model.Project;
import com.example.projectEdu.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class ProjectListController {

    private final ProjectService projectService;

    public ProjectListController(ProjectService projectService) {
        this.projectService = projectService;
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

        // Fetch all categories for the dropdown (make sure this method never returns null too)
        List<String> categories = projectService.getAllCategories();
        if (categories == null) {
            categories = Collections.emptyList();
        }
        System.out.println("Loaded projects count: " + projects.size());
        projects.forEach(p -> System.out.println(p.getTitle()));

        // Add attributes to model for Thymeleaf view
        model.addAttribute("projects", projects);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("searchTerm", search);
        model.addAttribute("activeTab", status != null ? status : "all");
        model.addAttribute("content", "fragments/project-list");

        return "layout";
    }
}
