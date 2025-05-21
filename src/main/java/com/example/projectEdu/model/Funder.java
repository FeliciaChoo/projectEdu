package com.example.projectEdu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "funder")
public class Funder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funder_id")
    private Long funderId;

    @NotBlank(message = "Funder name is required")
    @Column(name = "funder_name", nullable = false)
    private String funderName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name="email")
    private String funderEmail;

    @Column(name = "profile_url")
    private String funderProfileUrl;

    @OneToMany(mappedBy = "funder", cascade = CascadeType.ALL)
    private List<Fund> funds;


    // Constructors
    public Funder() {
    }

    public Funder(String funderName, String email, String profileUrl) {
        this.funderName = funderName;
        this.funderEmail = funderEmail;
        this.funderProfileUrl = funderProfileUrl;
    }

    // Getters and Setters
    public Long getFunderId() {
        return funderId;
    }

    public void setFunderId(Long funderId) {

        this.funderId = funderId;
    }

    public String getFunderName() {

        return funderName;
    }

    public void setFunderName(String funderName) {

        this.funderName = funderName;
    }

    public String getFunderEmail() {

        return funderEmail;
    }

    public void setFunderEmail(String email) {

        this.funderEmail = funderEmail;
    }

    public String getFunderProfileUrl() {
        if (funderProfileUrl == null || funderProfileUrl.isBlank()) {
            return "https://cdn-icons-png.flaticon.com/128/3177/3177440.png"; // default profile picture URL
        }
        return funderProfileUrl;
    }

    public void setFunderProfileUrl(String profileUrl) {
        this.funderProfileUrl = funderProfileUrl;
    }

    @Override
    public String toString() {
        return "Funder{" +
                "funderId=" + funderId +
                ", funderName='" + funderName + '\'' +
                ", email='" + funderEmail + '\'' +
                '}';
    }

    public List<Fund> getFunds() {
        return funds;
    }

    public void setFunds(List<Fund> funds) {
        this.funds = funds;
    }

}
