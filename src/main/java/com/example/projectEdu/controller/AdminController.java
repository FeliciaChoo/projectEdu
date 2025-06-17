package com.example.projectEdu.controller;

import com.example.projectEdu.service.StudentService;
import com.example.projectEdu.service.FunderService;
import com.example.projectEdu.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private FunderService funderService;

    @Autowired
    private ProjectService projectService;

    // Delete a student by ID
    @PostMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully!");
        return "redirect:/admin/students";
    }

    // Delete a funder by ID
    @PostMapping("/delete-funder/{id}")
    public String deleteFunder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        funderService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Funder deleted successfully!");
        return "redirect:/admin/funders";
    }

    // Delete a project by ID
    @PostMapping("/delete-project/{id}")
    public String deleteProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        projectService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Project deleted successfully!");
        return "redirect:/admin/projects";
    }
}
