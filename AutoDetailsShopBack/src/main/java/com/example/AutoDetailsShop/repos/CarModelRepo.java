package com.example.AutoDetailsShop.repos;


import com.example.AutoDetailsShop.domain.CarBrand;
import com.example.AutoDetailsShop.domain.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelRepo extends JpaRepository<CarModel, Long> {
}
