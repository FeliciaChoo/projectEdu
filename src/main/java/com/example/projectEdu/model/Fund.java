package com.example.projectEdu.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Fund")
public class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fund_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "funder_id")
    private Funder funder;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private BigDecimal amount;

    private String paymentMethod;

    private LocalDateTime transactionDate;

    // Constructors
    public Fund() {
    }

    public Fund(Funder funder, Project project, BigDecimal amount, String paymentMethod, LocalDateTime transactionDate) {
        this.funder = funder;
        this.project = project;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionDate = transactionDate;
    }

    // Getters and Setters
    public Long getFundId() {
        return id;
    }

    public void setFundId(Long fundId) {
        this.id = fundId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    @Override
    public String toString() {
        return "Fund{" +
                "id=" + id +
                ", funder=" + funder +
                ", project=" + project +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
