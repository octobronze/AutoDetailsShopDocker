package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.CarModel;
import com.example.AutoDetailsShop.repos.CarModelRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("carModelServiceImpl")
@Slf4j
public class CarModelImpl implements CarModelService{

    private final CarModelRepo carModelRepo;

    public CarModelImpl(CarModelRepo carModelRepo){
        this.carModelRepo = carModelRepo;
    }

    @Override
    public CarModel getById(Long id) {
        return carModelRepo.findById(id).orElse(null);
    }

    @Override
    public void save(CarModel carModel) {
        carModelRepo.save(carModel);
    }

    @Override
    public void delete(CarModel carModel) {
        carModelRepo.delete(carModel);
    }

    @Override
    public List<CarModel> getAll() {
        return carModelRepo.findAll();
    }
}
