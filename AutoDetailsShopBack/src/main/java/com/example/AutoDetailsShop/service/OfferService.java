package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.repos.OfferRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

public interface OfferService {

    Offer getById(Long id);

    void save(Offer offer);

    void delete(Offer offer);

    List<Offer> getAll(TypedQuery<Offer> query, int page, int size);
}
