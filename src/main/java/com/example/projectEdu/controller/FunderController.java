package com.example.projectEdu.controller;

import com.example.projectEdu.model.Fund;
import com.example.projectEdu.model.Project;
import com.example.projectEdu.model.Student;
import com.example.projectEdu.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/funder")
public class FunderController {

    private final ProjectService projectService;
    private final StudentService studentService;
    private final FunderService funderService;
    private final FundService fundService;


    public FunderController(ProjectService projectService, StudentService studentService, FunderService funderService, FundService fundService) {
        this.projectService = projectService;
        this.studentService = studentService;
        this.funderService = funderService;
        this.fundService = fundService;
    }


    @GetMapping("/funder-dashboard/{id}")
    public String showFunderDashboard(@PathVariable("id") Long id, Model model) {

        model.addAttribute("funder", funderService.findById(id).orElse(null));
        model.addAttribute("fund", fundService.findByFunderId(id));
        model.addAttribute("totalProjectsFunded", fundService.countByFunderId(id));
        model.addAttribute("totalAmountFunded", fundService.sumByFunderId(id));
        model.addAttribute("completedProjects", projectService.countCompletedProjectsByFunderId(id));
        model.addAttribute("title", "Funder Dashboard");
        model.addAttribute("content", "fragments/funder-dashboard");

        return "layout";
    }

    @GetMapping("/donate-project/{id}")
    public String showDonationForm(@PathVariable("id") Long projectId, Model model, Principal principal) {
        CustomUserDetails userDetails = (CustomUserDetails) ((Authentication) principal).getPrincipal();
        Long id = userDetails.getId();

        Project project = projectService.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + projectId));

        if (project.getEndDate().isBefore(LocalDate.now()) || project.getStatus().equals("Completed")) {
            model.addAttribute("project", project);
            return "project-ended";
        }

        Fund fund = new Fund();
        fund.setProject(project);
        fund.setFunder(funderService.findById(id).orElse(null));

        model.addAttribute("fund", fund);
        model.addAttribute("funder", fund.getFunder());
        model.addAttribute("project", project);
        model.addAttribute("title", "Donate to Project");
        model.addAttribute("content", "fragments/donate-project");

        return "layout";
    }

    @PostMapping("/add-fund")
    public String addNewFund(@Valid Fund fund, BindingResult result, Model model) {
        if (result.hasErrors()) {
            Project project = projectService.findById(fund.getProject().getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + fund.getProject().getProjectId()));
            model.addAttribute("project", project);
            model.addAttribute("funder", funderService.findById(fund.getFunder().getId()).orElse(null));
            model.addAttribute("content", "fragments/donor");

            return "layout";
        }
        Project project = projectService.findById(fund.getProject().getProjectId())
                .orElseThrow(()-> new IllegalArgumentException("Invalid project ID: " + fund.getProject().getProjectId()));

        BigDecimal currentAmount = project.getCurrentAmount();
        if (currentAmount == null) {
            currentAmount = BigDecimal.ZERO;
        }
        BigDecimal newAmount = currentAmount.add(fund.getAmount());
        project.setCurrentAmount(newAmount);

        projectService.updateProject(project);
        fundService.addNewFund(fund);
        return "payment-success";
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

    @PostMapping("/payment-success")
    public String showPaymentSuccess() {
        return "payment-success";
    }

    @GetMapping("/project-ended")
    public String showProjectEnded() {
        return "project-ended";
    }

}