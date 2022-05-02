import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CarBrandsInfo } from '../carBrands/carBrand-info';

const httpOptions = {
    headers: new HttpHeaders({'Content-type' : 'application/json'})
  };
  @Injectable({
    providedIn: 'root'
  })
  
  
  export class CarBrandsService{
  
    private carBrandsUrl = 'http://localhost:8080/api/car_brands/';
  
  
    getCarBrands(): Observable<CarBrandsInfo[]>{
        return this.http.get<CarBrandsInfo[]>(this.carBrandsUrl);
    }
  
    constructor(private http: HttpClient) { 
      
    }
  }