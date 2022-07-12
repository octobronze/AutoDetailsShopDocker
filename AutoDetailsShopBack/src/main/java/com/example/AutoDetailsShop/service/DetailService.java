package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Detail;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;

import java.util.List;

public interface DetailService {

    Detail getById(Long id) throws ValidationException;

    void save(Detail detail) throws ValidationException;

    void delete(Long id) throws ValidationException, NotFoundException;

    List<Detail> getAll();
}
