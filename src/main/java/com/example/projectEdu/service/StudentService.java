package com.example.projectEdu.service;

import com.example.projectEdu.model.Student;
import java.util.Optional;

public interface StudentService {
    Optional<Student> findById(Long id);
}
