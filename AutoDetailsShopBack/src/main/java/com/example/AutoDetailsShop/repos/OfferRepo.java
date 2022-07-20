package com.example.AutoDetailsShop.repos;

import com.example.AutoDetailsShop.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface OfferRepo extends JpaRepository<Offer, Long>, JpaSpecificationExecutor<Offer> {
}
