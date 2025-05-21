package com.example.projectEdu.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Fund")
public class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundId;

    @ManyToOne
    @JoinColumn(name = "funder_id")
    private Funder funder;

    @ManyToOne
    @JoinColumn(name = "project_id"
    )
    private Project project;

    private double amount;

    private String paymentMethod;

    private LocalDateTime transactionDate;

    // Constructors
    public Fund() {
    }

    public Fund(Funder funder, Project project, double amount, String paymentMethod, LocalDateTime transactionDate) {
        this.funder = funder;
        this.project = project;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionDate = transactionDate;
    }

    // Getters and Setters
    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }

    public Funder getFunder() {
        return funder;
    }

    public void setFunder(Funder funder) {
        this.funder = funder;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
