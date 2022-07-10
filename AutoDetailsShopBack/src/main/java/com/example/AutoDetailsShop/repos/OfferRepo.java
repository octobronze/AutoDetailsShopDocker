package com.example.AutoDetailsShop.repos;

import com.example.AutoDetailsShop.domain.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface OfferRepo extends JpaRepository<Offer, Long> {

}
