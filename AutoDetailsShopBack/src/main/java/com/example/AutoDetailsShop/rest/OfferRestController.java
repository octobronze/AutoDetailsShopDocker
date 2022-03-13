package com.example.AutoDetailsShop.rest;


import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.service.OfferService;
import dispatch.classic.Http;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Offer> getOffer(@PathVariable("id") Long offerId){

        if(offerId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Offer offer = offerService.getById(offerId);

        if(offer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Offer> saveOffer(@RequestBody @Valid Offer offer){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(offer == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        offerService.save(offer);

        return new ResponseEntity<>(offer, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Offer> updateOffer(@RequestBody @Valid Offer offer){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(offer == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        offerService.save(offer);

        return new ResponseEntity<>(offer, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Offer> deleteOffer(@PathVariable("id") Long offerId){
        if(offerId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Offer offer = offerService.getById(offerId);

        if(offer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        offerService.delete(offer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<Offer>> getAllOffers(){

        List<Offer> offers = offerService.getAll();

        if(offers.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(offers, HttpStatus.OK);
    }
}

