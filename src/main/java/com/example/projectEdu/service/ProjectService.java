package com.example.projectEdu.service;

import com.example.projectEdu.model.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects();
    List<Project> getProjectsByStatus(String status);
    List<String> getAllCategories();
    List<Project> findByStudentStudentId(Long studentId);
    Project findProjectById(Long projectId);
    Integer countProjectsByStudentId(Long studentId);
    Integer countCompletedProjectsByStudentId(Long studentId);
    Integer countActiveProjectsByStudentId(Long studentId);
    Project saveProject(Project project);


}
