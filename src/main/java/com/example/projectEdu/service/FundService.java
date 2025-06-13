package com.example.projectEdu.service;

import com.example.projectEdu.model.Fund;
import com.example.projectEdu.model.Funder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface FundService {


    BigDecimal sumByFunderId(Long id);

    BigDecimal sumByStudentId(Long id);

    int countByFunderId(Long id);

    int countByProjectId(Long projectId);

    Fund addNewFund (Fund fund);

    List<Fund> findByFunderId(Long id);

    void saveFunder(Funder funder);


}
