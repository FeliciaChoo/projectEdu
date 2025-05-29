package com.example.projectEdu.repository;

import com.example.projectEdu.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByStatus(String status);

    List<Project> findByStudentId(Long id);

    Optional<Project> findById(Long projectId);

    @Query("SELECT DISTINCT p.category FROM Project p")
    List<String> findAllCategories();

    Integer countByStudentId(Long id);
    Integer countByStudentIdAndStatus(Long id, String status);

    @Query(value = "SELECT COUNT(*) FROM fund f " +
            "JOIN project p ON f.project_id = p.project_id " +
            "WHERE f.funder_id = :id AND p.status = 'Completed'", nativeQuery = true)
    Integer countCompletedProjectsByFunderId(@Param("id") Long id);





}