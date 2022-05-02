package com.example.AutoDetailsShop.rest;

import com.example.AutoDetailsShop.domain.Detail;
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
    public ResponseEntity<Detail> getDetail(@PathVariable("id") Long detailId){

        if(detailId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Detail detail = detailService.getById(detailId);

        if(detail == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(detail, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Detail> saveDetail(@RequestBody @Valid Detail detail){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(detail == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        detailService.save(detail);

        return new ResponseEntity<>(detail, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Detail> updateDetail(@RequestBody @Valid Detail detail){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(detail == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        detailService.save(detail);

        return new ResponseEntity<>(detail, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value  = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Detail> deleteDetail(@PathVariable("id") Long detailId){
        if(detailId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Detail detail = detailService.getById(detailId);

        if(detail == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        detailService.delete(detail);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<Detail>> getAllDetails(){

        List<Detail> details = detailService.getAll();

        if(details.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(details, HttpStatus.OK);
    }

}
