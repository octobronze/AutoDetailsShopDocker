package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.documents.ExcelGenerator;
import com.example.AutoDetailsShop.documents.PdfGenerator;
import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.service.OfferService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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
    public ResponseEntity<Offer> deleteOffer(@PathVariable("id") Long offerId) throws ValidationException, NotFoundException {
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
            @RequestParam(defaultValue = "99999997", required = false) int size) {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<Offer> offers = offerService.getAll(detailName, carBrandName, carModelName, price, page, size);
        return new ResponseEntity<>(offers, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "pdf", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<InputStreamResource> generatePdf() throws DocumentException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=offersReport.pdf");
        httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
        ByteArrayInputStream in = PdfGenerator.generatePdfForOffers(offerService);
        return new ResponseEntity<>(new InputStreamResource(in), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "excel", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<InputStreamResource> generateExcel() throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=offersReport.xls");
        httpHeaders.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        ByteArrayInputStream in = ExcelGenerator.generateExcelForOffers(offerService);
        return new ResponseEntity<>(new InputStreamResource(in), httpHeaders, HttpStatus.OK);
    }

}

