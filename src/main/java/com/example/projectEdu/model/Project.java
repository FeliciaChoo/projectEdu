package com.example.projectEdu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @NotBlank(message = "Project title is required")
    @Size(max = 255, message = "Title must be less than 255 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Status is required")
    @Column(nullable = false)
    private String status;

    @NotBlank(message = "Description is required")
    @Lob
    private String description;

    @URL(message = "Survey link must be a valid URL")
    private String surveyLink;

    @NotNull(message = "Goal amount is required")
    @DecimalMin(value = "0.01", message = "Goal amount must be greater than 0")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal goalAmount;

    @DecimalMin(value = "0.00", message = "Current amount cannot be negative")
    @Column(precision = 19, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    @Column(nullable = false)
    private LocalDate endDate;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^[0-9-]+$", message = "Account number must contain only numbers and hyphens")
    private String accountNo;

    @NotBlank(message = "Account holder name is required")
    private String accountHolderName;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors remain the same
    public Project() {
        // Default constructor
    }

    public Project(String title, String status, String description, BigDecimal goalAmount,
                   LocalDate endDate) {
        this.title = title;
        this.status = status;
        this.description = description;
        this.goalAmount = goalAmount;
        this.endDate = endDate;
    }

    public Project(String title, String status, String description, String surveyLink,
                   BigDecimal goalAmount, BigDecimal currentAmount, LocalDate endDate,
                   String bankName, String accountNo, String accountHolderName) {
        this.title = title;
        this.status = status;
        this.description = description;
        this.surveyLink = surveyLink;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.endDate = endDate;
        this.bankName = bankName;
        this.accountNo = accountNo;
        this.accountHolderName = accountHolderName;
    }

    // Pre-update hook remains the same
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters remain the same
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    // ... (keep all other getters and setters exactly as they are) ...

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", surveyLink='" + surveyLink + '\'' +
                ", goalAmount=" + goalAmount +
                ", currentAmount=" + currentAmount +
                ", endDate=" + endDate +
                ", bankName='" + bankName + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}