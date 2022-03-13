package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.repos.OfferRepo;

import java.util.List;

public interface OfferService {

    Offer getById(Long id);

    void save(Offer offer);

    void delete(Offer offer);

    List<Offer> getAll();
}
