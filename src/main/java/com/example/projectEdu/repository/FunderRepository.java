package com.example.projectEdu.repository;

import com.example.projectEdu.model.Funder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FunderRepository extends JpaRepository<Funder, Long> {
    Optional<Funder> findByEmail(String email);
}
