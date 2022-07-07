package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.repos.OfferRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OfferService {

    Offer getById(Long id);

    void save(Offer offer);

    void delete(Offer offer);

    List<Offer> getAll();

    Page<Offer> getAllWithPagesByDetailName(String detailName, Pageable pageable);
}
