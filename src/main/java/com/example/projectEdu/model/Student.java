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
    private String studentName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name="email")
    private String studentEmail;

    @NotBlank(message = "University is required")
    @Column(name = "institution")
    private String institution;

    @Column(name = "profile_url")
    private String studentProfileUrl;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    // Constructors
    public Student() {
    }

    public Student(String studentNamename, String studentEmail, String institution, String studentProfileUrl) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.institution = institution;
        this.studentProfileUrl = studentProfileUrl;
    }

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String name) {
        this.studentName = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getStudentEmailEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentProfileUrl() {
        if (studentProfileUrl == null || studentProfileUrl.isBlank()) {
            return "https://cdn-icons-png.flaticon.com/128/3177/3177440.png"; // default profile picture URL
        }
        return studentProfileUrl;
    }

    public void setStudentProfileUrl(String studentProfileUrl) {
        this.studentProfileUrl = studentProfileUrl;
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
                ", studentName='" + studentName + '\'' +
                ", studentEmail='" + studentEmail + '\'' +
                ", institution='" + institution + '\'' +
                ", studentProfileUrl='" + studentProfileUrl + '\'' +
                '}';
    }
}
