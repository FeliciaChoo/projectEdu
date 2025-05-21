package com.example.projectEdu.service;

import com.example.projectEdu.model.Project;
import com.example.projectEdu.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getProjectsByStatus(String status) {
        return projectRepository.findByStatus(status);
    }

    public List<String> getAllCategories() {
        return projectRepository.findAllCategories();
    }
    public List<Project> findUpcomingProjects() {
        return projectRepository.findByStatusAndEndDateAfter("active", LocalDate.now());
    }



}