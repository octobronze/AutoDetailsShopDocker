package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.exceptions.NoDataException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.service.OfferService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/offers/")
public class OfferRestController {


    private final OfferService offerService;

    public OfferRestController(@Qualifier("offerServiceImpl") OfferService offerService) {
        this.offerService = offerService;
    }


    @RequestMapping(value  = "{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<Offer> getOffer(@PathVariable("id") Long offerId) throws NotFoundException, ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        Offer offer = offerService.getById(offerId);
        if(offer == null)
            throw new NotFoundException("Offer not found");
        return new ResponseEntity<>(offer, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Offer> saveOffer(@RequestBody @Valid Offer offer) throws ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        offerService.save(offer);
        return new ResponseEntity<>(offer, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Offer> updateOffer(@RequestBody @Valid Offer offer) throws ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        offerService.save(offer);
        return new ResponseEntity<>(offer, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Offer> deleteOffer(@PathVariable("id") Long offerId) throws ValidationException, NoDataException {
        HttpHeaders httpHeaders = new HttpHeaders();
        offerService.delete(offerId);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<Offer>> getAllOffers(
            @RequestParam(required = false) String detailName,
            @RequestParam(required = false) String carBrandName,
            @RequestParam(required = false) String carModelName,
            @RequestParam(required = false)BigDecimal price,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "99999997", required = false) int size) throws NoDataException {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<Offer> offers = offerService.getAll(detailName, carBrandName, carModelName, price, page, size);
        if(offers.isEmpty())
            throw new NoDataException("No data was found");
        return new ResponseEntity<>(offers, httpHeaders, HttpStatus.OK);
    }
}

