package com.example.projectEdu.repository;

import com.example.projectEdu.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByStatus(String status);

    List<Project> findByStudentStudentId(Long studentId);

    @Query(value = "SELECT * FROM project WHERE project_id = :projectId", nativeQuery = true)
    Project findProjectById(Long projectId);


    @Query("SELECT DISTINCT p.category FROM Project p")
    List<String> findAllCategories();

    @Query(value = "SELECT COUNT(*) FROM project WHERE student_id = :studentId", nativeQuery = true)
    Integer countProjectsByStudentId(Long studentId);

    @Query(value = "SELECT COUNT(*) FROM project WHERE student_id = :studentId AND status = 'completed'", nativeQuery = true)
    Integer countCompletedProjectsByStudentId(Long studentId);

    @Query(value = "SELECT COUNT(*) FROM project WHERE student_id = :studentId AND status = 'active'", nativeQuery = true)
    Integer countActiveProjectsByStudentId(Long studentId);
}