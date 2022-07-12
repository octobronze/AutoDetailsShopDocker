package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.domain.CarModel;
import com.example.AutoDetailsShop.exceptions.NoDataException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.service.CarModelService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/car_models/")
public class CarModelRestController {
    private final CarModelService carModelService;

    public CarModelRestController(@Qualifier("carModelServiceImpl") CarModelService carModelService){
        this.carModelService = carModelService;
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developer:read')")
    public ResponseEntity<CarModel> getCarModel(@PathVariable("id") Long carModelId) throws ValidationException, NotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        CarModel carModel = carModelService.getById(carModelId);
        if(carModel == null)
            throw new NotFoundException("Car model was not found");
        return new ResponseEntity<>(carModel, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<CarModel> saveCarModel(@RequestBody @Valid CarModel carModel) throws ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        carModelService.save(carModel);
        return new ResponseEntity<>(carModel, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<CarModel> updateCarModel(@RequestBody @Valid CarModel carModel) throws ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        carModelService.save(carModel);
        return new ResponseEntity<>(carModel, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<CarModel> deleteCarModel(@PathVariable("id") Long carModelId) throws ValidationException, NotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        carModelService.delete(carModelId);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<CarModel>> getAllCarModels() throws NoDataException {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<CarModel> carModels = carModelService.getAll();
        if(carModels.isEmpty())
            throw new NoDataException("No data was found");
        return new ResponseEntity<>(carModels, httpHeaders, HttpStatus.OK);
    }
}
