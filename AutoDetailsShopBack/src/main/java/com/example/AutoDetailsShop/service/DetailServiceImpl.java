package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Detail;
import com.example.AutoDetailsShop.repos.DetailRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("detailServiceImpl")
public class DetailServiceImpl implements DetailService{

    private final DetailRepo detailRepo;

    public DetailServiceImpl(DetailRepo detailRepo){
        this.detailRepo = detailRepo;
    }

    @Override
    public Detail getById(Long id) {
        return detailRepo.findById(id).orElse(null);
    }

    @Override
    public void save(Detail detail) {
        detailRepo.save(detail);
    }

    @Override
    public void delete(Detail detail) {
        detailRepo.delete(detail);
    }

    @Override
    public List<Detail> getAll() {
        return detailRepo.findAll();
    }
}
