package com.example.projectEdu.service;

import com.example.projectEdu.model.Funder;
import com.example.projectEdu.model.Student;

import java.util.Optional;
import java.util.List;

public interface FunderService {

    Optional<Funder> findById(Long id);

    List<Funder> findAll();
    void saveFunder(Funder funder);

    void deleteFunder(Funder funder);
    Integer totalFunders();





}
