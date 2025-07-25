package com.example.projectEdu.repository;

import com.example.projectEdu.model.Fund;
import com.example.projectEdu.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long> {

    List<Fund> findByFunderId(Long id);
    @Query(value = "SELECT COALESCE(SUM(amount),0) FROM fund WHERE funder_id = :id", nativeQuery = true)
    BigDecimal sumByFunderId(@Param("id") Long id);

    @Query(value = "SELECT COUNT(DISTINCT project_id) FROM fund WHERE funder_id = :id", nativeQuery = true)
    Integer countProjectsByFunderId(@Param("id") Long id);

    @Query(value = "SELECT COUNT(DISTINCT funder_id) FROM fund WHERE project_id = :id", nativeQuery = true)
    Integer countFundersByProjectId(@Param("id") Long projectId);

    @Query(value = "SELECT COALESCE(SUM(f.amount),0) FROM fund f JOIN project p ON f.project_id = p.project_id WHERE p.student_id = :id", nativeQuery = true)
    BigDecimal sumByStudentId(@Param("id") Long id);


}
