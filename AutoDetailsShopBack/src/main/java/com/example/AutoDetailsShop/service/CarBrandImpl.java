package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.CarBrand;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.repos.CarBrandRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("carBrandServiceImpl")
@Slf4j
public class CarBrandImpl implements CarBrandService {

    CarBrandRepo carBrandRepo;

    public CarBrandImpl(CarBrandRepo carBrandRepo){
        this.carBrandRepo = carBrandRepo;
    }

    @Override
    public CarBrand getById(Long id) throws ValidationException, NotFoundException {
        if(id == null)
            throw new ValidationException("Id is null");
        return carBrandRepo.findById(id).orElseThrow(() -> new NotFoundException("Car brand was not found"));
    }

    @Override
    public void delete(Long id) throws ValidationException, NotFoundException {
        if(id == null)
            throw new ValidationException("Id is null");
        CarBrand carBrand = carBrandRepo.findById(id).orElseThrow(() -> new NotFoundException("Car brand was not found"));
        carBrandRepo.delete(carBrand);
    }

    @Override
    public void save(CarBrand carBrand) throws ValidationException {
        if(carBrand == null)
            throw new ValidationException("Car brand is null");
        carBrandRepo.save(carBrand);
    }

    @Override
    public List<CarBrand> getAll() {
        return carBrandRepo.findAll();
    }
}
