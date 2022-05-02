package com.example.AutoDetailsShop.repos;

import com.example.AutoDetailsShop.domain.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarBrandRepo extends JpaRepository<CarBrand, Long> {
}
