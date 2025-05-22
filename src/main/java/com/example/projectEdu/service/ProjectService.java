package com.example.projectEdu.service;

import com.example.projectEdu.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<Project> getAllProjects();

    List<Project> getProjectsByStatus(String status);

    List<String> getAllCategories();

    List<Project> findByStudentId(Long id);

    Optional<Project> findById(Long projectId);

    int countByStudentId(Long id);

    int countByStudentIdAndStatus(Long id, String status);

    Optional<Project> findProjectById(Long id);

}
