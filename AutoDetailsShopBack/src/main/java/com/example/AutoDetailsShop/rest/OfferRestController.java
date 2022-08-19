package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.documents.ExcelGenerator;
import com.example.AutoDetailsShop.documents.PdfGenerator;
import com.example.AutoDetailsShop.domain.Offer;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.service.AuditService;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/domain/offers/")
public class OfferRestController {
    private final OfferService offerService;
    private final AuditService auditService;

    public OfferRestController(@Qualifier("offerServiceImpl") OfferService offerService,
                               @Qualifier("auditServiceImpl") AuditService auditService) {
        this.offerService = offerService;
        this.auditService = auditService;
    }


    @RequestMapping(value  = "{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<Offer> getOffer(@PathVariable("id") Long offerId, HttpServletRequest request) throws NotFoundException, ValidationException, InterruptedException, ExecutionException {
        HttpHeaders httpHeaders = new HttpHeaders();
        CompletableFuture<Offer> offer = offerService.getById(offerId);
        auditService.add(RequestMethod.GET.name(), request.getRequestURI(), request);
        CompletableFuture.allOf(offer).join();
        return new ResponseEntity<>(offer.get(), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Offer> saveOffer(@RequestBody @Valid Offer offer, HttpServletRequest request) throws ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        offerService.save(offer);
        auditService.add(RequestMethod.POST.name(), request.getRequestURI(), request);
        return new ResponseEntity<>(offer, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Offer> updateOffer(@RequestBody @Valid Offer offer, HttpServletRequest request) throws ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        offerService.save(offer);
        auditService.add(RequestMethod.PUT.name(), request.getRequestURI(), request);
        return new ResponseEntity<>(offer, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Offer> deleteOffer(@PathVariable("id") Long offerId, HttpServletRequest request) throws ValidationException, NotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        offerService.delete(offerId);
        auditService.add(RequestMethod.DELETE.name(), request.getRequestURI(), request);
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
            @RequestParam(defaultValue = "99999997", required = false) int size,
            HttpServletRequest request) throws ValidationException, ExecutionException, InterruptedException {
        HttpHeaders httpHeaders = new HttpHeaders();
        CompletableFuture<List<Offer>> offers = offerService.getAll(detailName, carBrandName, carModelName, price, page, size);
        auditService.add(RequestMethod.GET.name(), request.getRequestURI(), request);
        CompletableFuture.allOf(offers);
        return new ResponseEntity<>(offers.get(), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "pdf", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<InputStreamResource> generatePdf(HttpServletRequest request) throws DocumentException, ValidationException, ExecutionException, InterruptedException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=offersReport.pdf");
        httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
        CompletableFuture<ByteArrayInputStream> in = PdfGenerator.generatePdfForOffers(offerService);
        auditService.add(RequestMethod.GET.name(), request.getRequestURI(), request);
        CompletableFuture.allOf(in).join();
        return new ResponseEntity<>(new InputStreamResource(in.get()), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "excel", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<InputStreamResource> generateExcel(HttpServletRequest request) throws IOException, ValidationException, ExecutionException, InterruptedException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=offersReport.xls");
        httpHeaders.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        CompletableFuture<ByteArrayInputStream> in = ExcelGenerator.generateExcelForOffers(offerService);
        auditService.add(RequestMethod.GET.name(), request.getRequestURI(), request);
        CompletableFuture.allOf(in);
        return new ResponseEntity<>(new InputStreamResource(in.get()), httpHeaders, HttpStatus.OK);
    }
}

