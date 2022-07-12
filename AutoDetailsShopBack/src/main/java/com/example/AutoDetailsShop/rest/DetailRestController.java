package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.domain.Detail;
import com.example.AutoDetailsShop.exceptions.NoDataException;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.service.DetailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/details/")
public class DetailRestController {

    private final DetailService detailService;

    public DetailRestController(@Qualifier("detailServiceImpl") DetailService detailService){
        this.detailService = detailService;
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developer:read')")
    public ResponseEntity<Detail> getDetail(@PathVariable("id") Long detailId) throws ValidationException, NotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        Detail detail = detailService.getById(detailId);
        if(detail == null)
            throw new NotFoundException("Detail was not found");
        return new ResponseEntity<>(detail, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Detail> saveDetail(@RequestBody @Valid Detail detail) throws ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        detailService.save(detail);
        return new ResponseEntity<>(detail, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Detail> updateDetail(@RequestBody @Valid Detail detail) throws ValidationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        detailService.save(detail);
        return new ResponseEntity<>(detail, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Detail> deleteDetail(@PathVariable("id") Long detailId) throws ValidationException, NotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        detailService.delete(detailId);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<Detail>> getAllDetails() throws NoDataException {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<Detail> details = detailService.getAll();
        if(details.isEmpty())
            throw new NoDataException("Data was not found");
        return new ResponseEntity<>(details, httpHeaders, HttpStatus.OK);
    }

}
