package com.example.projectEdu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectEdu.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}