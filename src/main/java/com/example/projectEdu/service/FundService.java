package com.example.projectEdu.service;

import com.example.projectEdu.model.Fund;

import java.math.BigDecimal;
import java.util.List;

public interface FundService {


    BigDecimal sumByFunderId(Long id);

    int countByFunderId(Long id);

    Fund saveFund (Fund fund);

    List<Fund> findByFunderId(Long id);

}
