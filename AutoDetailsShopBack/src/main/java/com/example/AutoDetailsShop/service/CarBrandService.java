package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.CarBrand;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;

import java.util.List;

public interface CarBrandService {
    CarBrand getById(Long id) throws ValidationException, NotFoundException;

    void delete(Long id) throws ValidationException, NotFoundException;

    void save(CarBrand carBrand) throws ValidationException;

    List<CarBrand> getAll();
}
