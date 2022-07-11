package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.filters.OfferFilter;
import com.example.AutoDetailsShop.repos.OfferRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public Offer getById(Long id){
        return offerRepo.findById(id).orElse(null);
    }

    @Override
    public void save(Offer offer){
        offerRepo.save(offer);
    }

    @Override
    public void delete(Offer offer){
        offerRepo.delete(offer);
    }

    @Override
    public List<Offer> getAll(String detailName, String carBrandName, String carModelName, BigDecimal price, int page, int size) throws IOException {
        if(detailName == null && carBrandName == null
                && carModelName == null && price == null){
            return offerRepo.findAll(PageRequest.of(page, size)).getContent();
        }
        return offerRepo.findAll(OfferFilter.filter(detailName, carBrandName, carModelName, price), PageRequest.of(page, size)).getContent();
    }

}
