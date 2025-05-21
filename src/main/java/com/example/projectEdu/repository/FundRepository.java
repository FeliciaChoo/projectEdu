package com.example.projectEdu.repository;

import com.example.projectEdu.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long> {

    @Query(value = "SELECT SUM(amount) FROM fund WHERE funder_id = :id", nativeQuery = true)
    BigDecimal sumByFunderId(@Param("id") Long id);

    @Query(value = "SELECT COUNT(DISTINCT project_id) FROM fund WHERE funder_id = :id", nativeQuery = true)
    Integer countProjectsByFunderId(@Param("id") Long id);

}
