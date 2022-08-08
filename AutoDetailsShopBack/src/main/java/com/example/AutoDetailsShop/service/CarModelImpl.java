package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.CarModel;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.repos.CarModelRepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("carModelServiceImpl")
public class CarModelImpl implements CarModelService{
    private final CarModelRepo carModelRepo;

    public CarModelImpl(CarModelRepo carModelRepo){
        this.carModelRepo = carModelRepo;
    }

    @Override
    public CarModel getById(Long id) throws ValidationException, NotFoundException {
        if(id == null)
            throw new ValidationException("Id is null");
        return carModelRepo.findById(id).orElseThrow(() -> new NotFoundException("Car model was not found"));
    }

    @Override
    public void save(CarModel carModel) throws ValidationException {
        if(carModel == null)
            throw new ValidationException("Car model is null");
        carModelRepo.save(carModel);
    }

    @Override
    public void delete(Long id) throws ValidationException, NotFoundException {
        if(id == null)
            throw new ValidationException("Id is null");
        CarModel carModel = carModelRepo.findById(id).orElseThrow(() -> new NotFoundException("Car model was not found"));
        carModelRepo.delete(carModel);
    }

    @Override
    public List<CarModel> getAll(){
        return carModelRepo.findAll();
    }
}
