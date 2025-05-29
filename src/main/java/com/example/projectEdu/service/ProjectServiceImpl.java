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
    public int countByStudentId(Long id) {
        return projectRepository.countByStudentId(id);
    }

    @Override
    public int countByStudentIdAndStatus(Long id, String status) {
        return projectRepository.countByStudentIdAndStatus(id, status);
    }

    @Override
    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }

    @Override
    public void updateProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    public int countCompletedProjectsByFunderId(Long id) {
        return projectRepository.countCompletedProjectsByFunderId(id);
    }
}
