package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Detail;

import java.util.List;

public interface DetailService {

    Detail getById(Long id);

    void save(Detail detail);

    void delete(Detail detail);

    List<Detail> getAll();
}
