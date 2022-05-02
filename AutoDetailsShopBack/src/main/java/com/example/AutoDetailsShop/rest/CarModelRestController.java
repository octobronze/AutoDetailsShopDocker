package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.domain.CarModel;
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
    public ResponseEntity<CarModel> getCarModel(@PathVariable("id") Long carModelId){

        if(carModelId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CarModel carModel = carModelService.getById(carModelId);

        if(carModel == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(carModel, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<CarModel> saveCarModel(@RequestBody @Valid CarModel carModel){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(carModel == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        carModelService.save(carModel);

        return new ResponseEntity<>(carModel, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<CarModel> updateCarModel(@RequestBody @Valid CarModel carModel){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(carModel == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        carModelService.save(carModel);

        return new ResponseEntity<>(carModel, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<CarModel> deleteCarModel(@PathVariable("id") Long carModelId){
        if(carModelId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CarModel carModel = carModelService.getById(carModelId);

        if(carModel == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        carModelService.delete(carModel);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<CarModel>> getAllCarModels(){

        List<CarModel> carModels = carModelService.getAll();

        if(carModels.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(carModels, HttpStatus.OK);
    }
}
