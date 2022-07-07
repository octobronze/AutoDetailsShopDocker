package com.example.AutoDetailsShop.repos;

import com.example.AutoDetailsShop.domain.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OfferRepo extends JpaRepository<Offer, Long> {
    Page<Offer> findOfferByDetail_DetailName(String detailName, Pageable pageable);
}
