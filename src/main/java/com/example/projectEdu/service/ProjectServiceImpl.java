package com.example.projectEdu.service;

import com.example.projectEdu.model.Project;
import com.example.projectEdu.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {  // use implements, not extends

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getProjectsByStatus(String status) {
        return projectRepository.findByStatus(status);
    }

    @Override
    public List<String> getAllCategories() {
        return projectRepository.findAllCategories();
    }


    @Override
    public List<Project> findByStudentStudentId(Long studentId) {
        return projectRepository.findByStudentStudentId(studentId);
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    @Override
    public Integer countProjectsByStudentId(Long studentId) {
        return projectRepository.countProjectsByStudentId(studentId);
    }

    @Override
    public Integer countCompletedProjectsByStudentId(Long studentId) {
        return projectRepository.countCompletedProjectsByStudentId(studentId);
    }

    @Override
    public Integer countActiveProjectsByStudentId(Long studentId) {
        return projectRepository.countActiveProjectsByStudentId(studentId);
    }
}
