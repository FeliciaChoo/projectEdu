package com.example.projectEdu.repository;

import com.example.projectEdu.model.Funder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface FunderRepository extends JpaRepository<Funder, Long> {
    Optional<Funder> findByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM funder", nativeQuery = true)
    Integer totalFunders();
}
