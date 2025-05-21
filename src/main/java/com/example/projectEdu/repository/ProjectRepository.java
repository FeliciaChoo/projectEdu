package com.example.projectEdu.repository;

import com.example.projectEdu.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByStatus(String status);

    List<Project> findByStudentId(Long id);

    Optional<Project> findById(Long projectId);

    @Query("SELECT DISTINCT p.category FROM Project p")
    List<String> findAllCategories();

    @Query(value = "SELECT COUNT(*) FROM project WHERE student_id = :id", nativeQuery = true)
    Integer countProjectsByStudentId(Long studentId);

    @Query(value = "SELECT COUNT(*) FROM project WHERE student_id = :id AND status = 'completed'", nativeQuery = true)
    Integer countCompletedProjectsByStudentId(Long id);

    @Query(value = "SELECT COUNT(*) FROM project WHERE student_id = :id AND status = 'active'", nativeQuery = true)
    Integer countActiveProjectsByStudentId(Long id);
}