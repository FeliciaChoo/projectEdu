package com.example.projectEdu.service;

import com.example.projectEdu.model.Student;
import com.example.projectEdu.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> findById(Long id){
        return studentRepository.findById(id);
    }
    @Override
    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }
    @Override
    public void saveStudent(Student student) {

        System.out.println("Saving student to DB: " + student);
        studentRepository.save(student);
    }

    @Override
    public int totalStudents() {
        return studentRepository.totalStudents();
    }

    @Override
    public List<Student> findAll(){
        return studentRepository.findAll();
    }

}
