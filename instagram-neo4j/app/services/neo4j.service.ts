import { Injectable } from "@angular/core"
import type { HttpClient } from "@angular/common/http"
import type { Observable } from "rxjs"
import type { Publicacion, Usuario, Hashtag } from "../models/models"

@Injectable({
  providedIn: "root",
})
export class Neo4jService {
  private apiUrl = "http://localhost:3000/api" // Ajusta según tu backend

  constructor(private http: HttpClient) {}

  // Publicaciones
  getPublicaciones(): Observable<Publicacion[]> {
    return this.http.get<Publicacion[]>(`${this.apiUrl}/publicaciones`)
  }

  getPublicacionById(id: string): Observable<Publicacion> {
    return this.http.get<Publicacion>(`${this.apiUrl}/publicaciones/${id}`)
  }

  createPublicacion(publicacion: Publicacion): Observable<Publicacion> {
    return this.http.post<Publicacion>(`${this.apiUrl}/publicaciones`, publicacion)
  }

  updatePublicacion(id: string, publicacion: Publicacion): Observable<Publicacion> {
    return this.http.put<Publicacion>(`${this.apiUrl}/publicaciones/${id}`, publicacion)
  }

  deletePublicacion(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/publicaciones/${id}`)
  }

  // Hashtags
  getHashtags(): Observable<Hashtag[]> {
    return this.http.get<Hashtag[]>(`${this.apiUrl}/hashtags`)
  }

  // Usuarios
  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/usuarios`)
  }

  // Likes
  likePublicacion(idPublicacion: string, idUsuario: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/likes`, { id_publicacion: idPublicacion, id_usuario: idUsuario })
  }

  unlikePublicacion(idPublicacion: string, idUsuario: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/likes/${idPublicacion}/${idUsuario}`)
  }

  // Publicaciones por hashtag
  getPublicacionesByHashtag(idHashtag: string): Observable<Publicacion[]> {
    return this.http.get<Publicacion[]>(`${this.apiUrl}/hashtags/${idHashtag}/publicaciones`)
  }

  // Publicaciones de un usuario
  getPublicacionesByUsuario(idUsuario: string): Observable<Publicacion[]> {
    return this.http.get<Publicacion[]>(`${this.apiUrl}/usuarios/${idUsuario}/publicaciones`)
  }
}
