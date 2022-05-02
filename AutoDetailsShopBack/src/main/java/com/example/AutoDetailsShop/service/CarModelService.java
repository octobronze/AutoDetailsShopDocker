package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.CarModel;

import java.util.List;

public interface CarModelService {
    CarModel getById(Long id);

    void save(CarModel carModel);

    void delete(CarModel carModel);

    List<CarModel> getAll();
}
