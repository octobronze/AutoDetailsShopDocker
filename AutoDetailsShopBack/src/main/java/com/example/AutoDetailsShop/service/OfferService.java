package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface OfferService {

    CompletableFuture<Offer> getById(Long id) throws ValidationException, NotFoundException, InterruptedException;

    void save(Offer offer) throws ValidationException;

    void delete(Long id);

    CompletableFuture<List<Offer>> getAll(String detailName, String carBrandName, String carModelName, BigDecimal price, int page, int size);

    List<Offer> getAll();
}
