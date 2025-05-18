package com.example.projectEdu.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Student {
    private String name;
    private String id;
    private String university;
    private String otherUniversity;
    private String email;

    // Getters and Setters

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Student{" +
                "studentName='" + name + '\'' +
                ", studentID='" + id + '\'' +
                ", university='" + university + '\'' +
                ", otherUniversity='" + otherUniversity + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
