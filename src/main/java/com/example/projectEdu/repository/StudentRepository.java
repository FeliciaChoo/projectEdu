package com.example.projectEdu.repository;

import com.example.projectEdu.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer totalStudents();

}
