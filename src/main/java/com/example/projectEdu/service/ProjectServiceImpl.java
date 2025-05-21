package com.example.projectEdu.service;

import com.example.projectEdu.model.Project;
import com.example.projectEdu.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

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
    public List<Project> findByStudentId(Long id) {
        return projectRepository.findByStudentId(id);
    }

    @Override
    public Optional<Project> findById(Long projectId){
        return projectRepository.findById(projectId);
    }

    @Override
    public Integer countProjectsByStudentId(Long id) {
        return projectRepository.countProjectsByStudentId(id);
    }

    @Override
    public Integer countCompletedProjectsByStudentId(Long id) {
        return projectRepository.countCompletedProjectsByStudentId(id);
    }

    @Override
    public Integer countActiveProjectsByStudentId(Long id) {
        return projectRepository.countActiveProjectsByStudentId(id);
    }
}
