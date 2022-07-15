package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.CarModel;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;

import java.util.List;

public interface CarModelService {
    CarModel getById(Long id) throws ValidationException, NotFoundException;

    void save(CarModel carModel) throws ValidationException;

    void delete(Long id) throws ValidationException, NotFoundException;

    List<CarModel> getAll();
}
