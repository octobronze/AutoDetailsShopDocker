package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.CarBrand;
import com.example.AutoDetailsShop.repos.CarBrandRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("carBrandServiceImpl")
@Slf4j
public class CarBrandImpl implements CarBrandService {

    CarBrandRepo carBrandRepo;

    public CarBrandImpl(CarBrandRepo carBrandRepo){
        this.carBrandRepo = carBrandRepo;
    }

    @Override
    public CarBrand getById(Long id) {
        return carBrandRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(CarBrand carBrand) {
        carBrandRepo.delete(carBrand);
    }

    @Override
    public void save(CarBrand carBrand) {
        carBrandRepo.save(carBrand);
    }

    @Override
    public List<CarBrand> getAll() {
        return carBrandRepo.findAll();
    }
}
