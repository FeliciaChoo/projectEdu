package com.example.projectEdu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @NotBlank(message = "Student name is required")
    @Column(name = "student_name", nullable = false)
    private String name;

    @NotBlank(message = "University student ID is required")
    @Column(name = "student_uni_id", nullable = false, unique = true)
    private String studentUniId;

    @NotBlank(message = "University is required")
    @Column(nullable = false)
    private String university;

    @Column(name = "other_university")
    private String otherUniversity;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    // Constructors
    public Student() {
    }

    public Student(String name, String studentUniId, String university, String otherUniversity, String email) {
        this.name = name;
        this.studentUniId = studentUniId;
        this.university = university;
        this.otherUniversity = otherUniversity;
        this.email = email;
    }

    // Getters and Setters

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentUniId() {
        return studentUniId;
    }

    public void setStudentUniId(String studentUniId) {
        this.studentUniId = studentUniId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getOtherUniversity() {
        return otherUniversity;
    }

    public void setOtherUniversity(String otherUniversity) {
        this.otherUniversity = otherUniversity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", studentUniId='" + studentUniId + '\'' +
                ", university='" + university + '\'' +
                ", otherUniversity='" + otherUniversity + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
