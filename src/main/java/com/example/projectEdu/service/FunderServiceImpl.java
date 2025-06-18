package com.example.projectEdu.service;

import com.example.projectEdu.model.Funder;
import com.example.projectEdu.model.Student;
import com.example.projectEdu.repository.FunderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FunderServiceImpl implements FunderService {

    private final FunderRepository funderRepository;

    public FunderServiceImpl(FunderRepository funderRepository) {
        this.funderRepository = funderRepository;
    }

    @Override
    public Optional<Funder> findById(Long id){
        return funderRepository.findById(id);
    }

    @Override
    public void saveFunder(Funder funder) {
        funderRepository.save(funder);
    }

    public void deleteFunder(Funder funder){
        funderRepository.delete(funder);
    }

    public Integer totalFunders() {
        return funderRepository.totalFunders();
    }

    public List<Funder> findAll() {
        return funderRepository.findAll();
    }

}
