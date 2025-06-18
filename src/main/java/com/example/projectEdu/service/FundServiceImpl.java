package com.example.projectEdu.service;

import com.example.projectEdu.model.Fund;
import com.example.projectEdu.model.Funder;
import com.example.projectEdu.repository.FundRepository;
import com.example.projectEdu.repository.FunderRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class FundServiceImpl implements FundService {

    private final FundRepository fundRepository;
    private final FunderRepository funderRepository;

    public FundServiceImpl(FundRepository fundRepository, FunderRepository funderRepository) {
        this.fundRepository = fundRepository;
        this.funderRepository = funderRepository;
    }

    @Override
    public BigDecimal sumByFunderId(Long id) {
        BigDecimal total = fundRepository.sumByFunderId(id);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public int countByFunderId(Long id) {
        Integer count = fundRepository.countProjectsByFunderId(id);
        return count != null ? count : 0;
    }

    @Override
    public int countByProjectId(Long projectId) {
        Integer count = fundRepository.countFundersByProjectId(projectId);
        return count != null ? count : 0;
    }

    @Override
    public Fund addNewFund (Fund fund){
        return fundRepository.save(fund);
    }

    @Override
    public List<Fund> findByFunderId(Long id) {
        return fundRepository.findByFunderId(id);
    }

    @Override
    public void saveFunder(Funder funder) {
        funderRepository.save(funder);
    }

    public BigDecimal sumByStudentId(Long id){
        return fundRepository.sumByStudentId(id);
    }

}
