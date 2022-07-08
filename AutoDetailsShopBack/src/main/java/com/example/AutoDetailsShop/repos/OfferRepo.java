package com.example.AutoDetailsShop.repos;

import com.example.AutoDetailsShop.domain.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface OfferRepo extends JpaRepository<Offer, Long> {

    @Query(value = "SELECT o FROM Offer o WHERE (:detailName is null OR o.detail.detailName = :detailName)" +
            "AND (:carBrandName is null OR o.carBrand.carBrandName = :carBrandName)" +
            "AND (:carModelName is null OR o.carModel.carModelName = :carModelName) " +
            "AND (:price is null OR o.price = :price)")
    Page<Offer> findALl(@Param("detailName") String detailName, @Param("carBrandName") String carBrandName, @Param("carModelName") String carModelName, @Param("price") BigDecimal price, Pageable pageable);
}
