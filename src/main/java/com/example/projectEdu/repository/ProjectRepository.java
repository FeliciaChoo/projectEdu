package com.example.projectEdu.repository;

import com.example.projectEdu.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByStatus(String status);

    @Query("SELECT DISTINCT p.category FROM Project p")
    List<String> findAllCategories();
}