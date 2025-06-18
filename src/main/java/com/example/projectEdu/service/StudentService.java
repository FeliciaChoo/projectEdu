package com.example.projectEdu.service;

import com.example.projectEdu.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Optional<Student> findById(Long id);
    void saveStudent (Student student);
    void deleteStudent(Student student);

    int totalStudents();

    List<Student> findAll();

}
