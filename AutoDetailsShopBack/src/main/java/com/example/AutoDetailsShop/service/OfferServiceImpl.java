package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.filters.Pagination;
import com.example.AutoDetailsShop.repos.OfferRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
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
    public List<Offer> getAll(TypedQuery<Offer> query, int page, int size) {
        return Pagination.getPage(query, page, size);
    }

}
