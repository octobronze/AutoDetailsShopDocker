package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.filters.OfferFilter;
import com.example.AutoDetailsShop.repos.OfferRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service("offerServiceImpl")
public class OfferServiceImpl implements OfferService{
    private final OfferRepo offerRepo;

    public OfferServiceImpl(OfferRepo offerRepo) {
        this.offerRepo = offerRepo;
    }

    @Override
    @Async
    public CompletableFuture<Offer> getById(Long id) throws ValidationException, NotFoundException, InterruptedException {
        Thread.sleep(1000);
        return CompletableFuture.completedFuture(offerRepo.findById(id).orElseThrow(() -> new NotFoundException("Offer was not found")));
    }

    @Override
    @Async
    public void save(Offer offer) throws ValidationException {
        if(offer == null)
            throw new ValidationException("Offer is be null");
        offerRepo.save(offer);
    }

    @Override
    @Async
    public void delete(Long id) {
        if(id == null)
            throw new ValidationException("Id is null");
        Offer offer = offerRepo.findById(id).orElseThrow(() -> new NotFoundException("Offer was not found"));
        offerRepo.delete(offer);
    }

    @Override
    @Async
    public CompletableFuture<List<Offer>> getAll(String detailName, String carBrandName, String carModelName, BigDecimal price, int page, int size) {
        List<Offer> offers;
        if(detailName == null && carBrandName == null
                && carModelName == null && price == null){
            offers = offerRepo.findAll(PageRequest.of(page, size)).getContent();
        }else{
            offers = offerRepo.findAll(OfferFilter.filterByAllFields(detailName, carBrandName, carModelName, price), PageRequest.of(page, size)).getContent();
        }
        return CompletableFuture.completedFuture(offers);
    }

    @Override
    public List<Offer> getAll(){
        return offerRepo.findAll();
    }
}
