package com.example.projectEdu.service;

import java.math.BigDecimal;

public interface FundService {


    BigDecimal sumByFunderId(Long id);

    int countByFunderId(Long id);

}
