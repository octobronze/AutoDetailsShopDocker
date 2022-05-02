import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { UsersInfo } from '../users/users-info';



const httpOptions = {
  headers: new HttpHeaders({'Content-type' : 'application/json'})
};
@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private usersUrl = 'http://localhost:8080/api/users/';


  getUsers(): Observable<any[]>{
      return this.http.get<any[]>(this.usersUrl);
  }

  addUser(user: any): Observable<any>{
      return this.http.post<any>(this.usersUrl, user, httpOptions);
  }

  removeUser(id: number): Observable<string>{
      return this.http.delete<string>(this.usersUrl + id.toString(), httpOptions);
  }

  setUSer(id: number, any: any): Observable<any>{
    return this.http.post<any>(this.usersUrl + id.toString(), any, httpOptions);
  }

  constructor(private http: HttpClient) { 
    
  }
}
