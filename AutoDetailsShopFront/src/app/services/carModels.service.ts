import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CarModelsInfo } from '../carModels/carModel-info';

const httpOptions = {
    headers: new HttpHeaders({'Content-type' : 'application/json'})
  };
  @Injectable({
    providedIn: 'root'
  })
  
  
  export class CarModelsService{
  
    private carModelssUrl = 'http://localhost:8080/api/car_models/';
  
  
    getCarModels(): Observable<CarModelsInfo[]>{
        return this.http.get<CarModelsInfo[]>(this.carModelssUrl);
    }
  
    constructor(private http: HttpClient) { 
      
    }
  }