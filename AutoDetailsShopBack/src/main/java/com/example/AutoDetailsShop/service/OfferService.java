package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import java.math.BigDecimal;
import java.util.List;

public interface OfferService {

    Offer getById(Long id) throws ValidationException;

    void save(Offer offer) throws ValidationException;

    void delete(Long id) throws ValidationException, NotFoundException;

    List<Offer> getAll(String detailName, String carBrandName, String carModelName, BigDecimal price, int page, int size);
}
