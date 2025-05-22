import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:3000'; // Cambiá esto según tu backend

  constructor(private http: HttpClient) {}

  getUsuarios(): Observable<any> {
    return this.http.get(`${this.baseUrl}/usuarios`);
  }

  crearUsuario(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/usuarios`, data);
  }

  crearPublicacion(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/publicaciones`, data);
  }

  darLike(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/likes`, data);
  }
}
