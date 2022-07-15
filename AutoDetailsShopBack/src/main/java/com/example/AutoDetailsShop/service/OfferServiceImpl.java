package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.filters.OfferFilter;
import com.example.AutoDetailsShop.repos.OfferRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service("offerServiceImpl")
public class OfferServiceImpl implements OfferService{

    private final OfferRepo offerRepo;

    public OfferServiceImpl(OfferRepo offerRepo) {
        this.offerRepo = offerRepo;
    }

    @Override
    public Offer getById(Long id) throws ValidationException, NotFoundException {
        return offerRepo.findById(id).orElseThrow(() -> new NotFoundException("Offer was not found"));
    }

    @Override
    public void save(Offer offer) throws ValidationException {
        if(offer == null)
            throw new ValidationException("Offer is be null");
        offerRepo.save(offer);
    }

    @Override
    public void delete(Long id) throws ValidationException, NotFoundException {
        if(id == null)
            throw new ValidationException("Id is null");
        Offer offer = offerRepo.findById(id).orElseThrow(() -> new NotFoundException("Offer was not found"));
        offerRepo.delete(offer);
    }

    @Override
    public List<Offer> getAll(String detailName, String carBrandName, String carModelName, BigDecimal price, int page, int size) {
        List<Offer> offers;
        if(detailName == null && carBrandName == null
                && carModelName == null && price == null){
            offers = offerRepo.findAll(PageRequest.of(page, size)).getContent();
        }else{
            offers = offerRepo.findAll(OfferFilter.filterByAllFields(detailName, carBrandName, carModelName, price), PageRequest.of(page, size)).getContent();
        }
        return offers;
    }

    @Override
    public List<Offer> getAll(){
        return offerRepo.findAll();
    }

}
