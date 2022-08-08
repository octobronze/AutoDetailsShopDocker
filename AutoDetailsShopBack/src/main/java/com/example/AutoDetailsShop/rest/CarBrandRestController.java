package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.domain.CarBrand;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
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
@RequestMapping("/api/domain/car_brands/")
public class CarBrandRestController {
    private final CarBrandService carBrandService;

    public CarBrandRestController(@Qualifier("carBrandServiceImpl") CarBrandService carBrandService){
        this.carBrandService = carBrandService;
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developer:read')")
    public ResponseEntity<CarBrand> getCarBrand(@PathVariable("id") Long carBrandId) throws ValidationException, NotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        CarBrand carBrand = carBrandService.getById(carBrandId);
        return new ResponseEntity<>(carBrand, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<CarBrand> saveCarBrand(@RequestBody @Valid CarBrand carBrand) throws ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        carBrandService.save(carBrand);
        return new ResponseEntity<>(carBrand, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<CarBrand> updateCarBrand(@RequestBody @Valid CarBrand carBrand) throws ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        carBrandService.save(carBrand);
        return new ResponseEntity<>(carBrand, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<CarBrand> deleteCarBrand(@PathVariable("id") Long carBrandId) throws ValidationException, NotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        carBrandService.delete(carBrandId);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<CarBrand>> getAllCarBrands() {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<CarBrand> carBrands = carBrandService.getAll();
        return new ResponseEntity<>(carBrands, httpHeaders, HttpStatus.OK);
    }
}
