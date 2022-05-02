import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DetailsInfo } from '../details/details-info';

const httpOptions = {
    headers: new HttpHeaders({'Content-type' : 'application/json'})
  };
  @Injectable({
    providedIn: 'root'
  })
  
  
  export class DetailsService{
  
    private offersUrl = 'http://localhost:8080/api/details/';
  
  
    getDetails(): Observable<DetailsInfo[]>{
        return this.http.get<DetailsInfo[]>(this.offersUrl);
    }
  
    constructor(private http: HttpClient) { 
      
    }
  }