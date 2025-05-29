package com.example.projectEdu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "student")
public class Student {


    private  String otherUniversity;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @NotBlank(message = "Student name is required")
    @Column(name = "student_name")
    private String name;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name="email")
    private String email;

    @NotBlank(message = "University is required")
    @Column(name = "institution")
    private String university;

    @Column(name = "profile_url")
    private String profileUrl;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    // Constructors
    public Student() {
    }




    // Updated constructor to include studentUniId
    public Student( String name, String email, String university, String otherUniversity, String profileUrl) {
        this.name = name;
        this.email = email;
        this.university = university;
        this.otherUniversity = otherUniversity;
        this.profileUrl = profileUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileUrl() {
        if (profileUrl == null || profileUrl.isBlank()) {
            return "https://cdn-icons-png.flaticon.com/128/3177/3177440.png"; // default profile picture URL
        }
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
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
                "studentId=" + id +
                ", studentName='" + name + '\'' +
                ", studentEmail='" + email + '\'' +
                ", institution='" + university + '\'' +
                ", otherUniversity='" + otherUniversity + '\'' +
                ", studentProfileUrl='" + profileUrl + '\'' +
                '}';
    }
}
