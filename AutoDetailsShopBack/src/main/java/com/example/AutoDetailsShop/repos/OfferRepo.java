package com.example.AutoDetailsShop.repos;

import com.example.AutoDetailsShop.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepo extends JpaRepository<Offer, Long> {
}
