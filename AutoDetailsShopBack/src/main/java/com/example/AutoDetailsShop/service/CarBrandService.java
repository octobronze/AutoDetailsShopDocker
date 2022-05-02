package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.CarBrand;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CarBrandService {
    CarBrand getById(Long id);

    void delete(CarBrand carBrand);

    void save(CarBrand carBrand);

    List<CarBrand> getAll();
}
