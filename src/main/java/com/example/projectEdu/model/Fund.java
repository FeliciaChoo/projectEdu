package com.example.projectEdu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Fund")
public class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundId;
}
