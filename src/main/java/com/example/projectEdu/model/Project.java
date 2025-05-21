package com.example.projectEdu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

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

    @NotBlank(message = "Category is required")
    @Column(nullable = false)
    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Fund> funds;

    // Default constructor
    public Project() {}

    // Constructor with essential fields
    public Project(String title, String status, String description, String category,
                   BigDecimal goalAmount, LocalDate endDate) {
        this.title = title;
        this.status = status;
        this.description = description;
        this.category = category;
        this.goalAmount = goalAmount;
        this.endDate = endDate;
    }

    // Constructor with all major fields
    public Project(String title, String status, String description, String surveyLink,
                   BigDecimal goalAmount, BigDecimal currentAmount, LocalDate endDate,
                   String bankName, String accountNo, String accountHolderName, String imageUrl) {
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
        this.imageUrl = imageUrl;
    }


    // Getters and Setters
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    // Getters and Setters
    public Long getProjectId() {
        return projectId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSurveyLink() {
        return surveyLink;
    }

    public void setSurveyLink(String surveyLink) {
        this.surveyLink = surveyLink;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Transient methods
    @Transient
    public int getFundedPercentage() {
        if (goalAmount != null && goalAmount.compareTo(BigDecimal.ZERO) > 0) {
            return currentAmount.multiply(BigDecimal.valueOf(100))
                    .divide(goalAmount, 0, BigDecimal.ROUND_DOWN)
                    .intValue();
        }
        return 0;
    }

    @Transient
    public String getShortDescription() {
        if (description == null) {
            return "";
        }
        return description.length() > 100
                ? description.substring(0, 100) + "..."
                : description;
    }


    // JPA lifecycle callback
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public List<Fund> getFunds() {
        return funds;
    }


    public void setFunds(List<Fund> funds) {
        this.funds = funds;
    }

    // toString method
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
                ", category='" + category + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }


}
