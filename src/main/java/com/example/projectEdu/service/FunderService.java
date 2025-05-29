package com.example.projectEdu.service;

import com.example.projectEdu.model.Funder;
import com.example.projectEdu.model.Student;

import java.util.Optional;

public interface FunderService {

    Optional<Funder> findById(Long id);
    void saveFunder(Funder funder);
}
