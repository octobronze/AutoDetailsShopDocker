package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Detail;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
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
    public Detail getById(Long id) throws ValidationException {
        if(id == null)
            throw new ValidationException("Id is null");
        return detailRepo.findById(id).orElse(null);
    }

    @Override
    public void save(Detail detail) throws ValidationException {
        if(detail == null)
            throw new ValidationException("Detail is null");
        detailRepo.save(detail);
    }

    @Override
    public void delete(Long id) throws ValidationException, NotFoundException {
        if(id == null)
            throw new ValidationException("Id is null");
        Detail detail = detailRepo.findById(id).orElseThrow(() -> new NotFoundException("Detail was not found"));
        detailRepo.delete(detail);
    }

    @Override
    public List<Detail> getAll() {
        return detailRepo.findAll();
    }
}
