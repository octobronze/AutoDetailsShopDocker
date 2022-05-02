package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.domain.CarBrand;
import com.example.AutoDetailsShop.service.CarBrandService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/car_brands/")
public class CarBrandRestController {
    private final CarBrandService carBrandService;

    public CarBrandRestController(@Qualifier("carBrandServiceImpl") CarBrandService carBrandService){
        this.carBrandService = carBrandService;
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developer:read')")
    public ResponseEntity<CarBrand> getCarBrand(@PathVariable("id") Long carBrandId){

        if(carBrandId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CarBrand carBrand = carBrandService.getById(carBrandId);

        if(carBrand == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(carBrand, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<CarBrand> saveCarBrand(@RequestBody @Valid CarBrand carBrand){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(carBrand == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        carBrandService.save(carBrand);

        return new ResponseEntity<>(carBrand, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<CarBrand> updateCarBrand(@RequestBody @Valid CarBrand carBrand){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(carBrand == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        carBrandService.save(carBrand);

        return new ResponseEntity<>(carBrand, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<CarBrand> deleteCarBrand(@PathVariable("id") Long carBrandId){
        if(carBrandId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CarBrand carBrand = carBrandService.getById(carBrandId);

        if(carBrand == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        carBrandService.delete(carBrand);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<CarBrand>> getAllCarBrands(){

        List<CarBrand> carBrands = carBrandService.getAll();

        if(carBrands.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(carBrands, HttpStatus.OK);
    }
}
