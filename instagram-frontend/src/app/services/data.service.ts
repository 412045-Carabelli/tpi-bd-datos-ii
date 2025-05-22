// src/app/services/publicaciones-state.service.ts
import { Injectable } from '@angular/core';
import { Publicacion } from '../models/publicacion.model';
import { Hashtag } from '../models/hashtag.model';
import { Usuario } from '../models/usuario.model';
import { Like } from '../models/like.model';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private _usuarios: Usuario[] = [
    {
      id: 1,
      username: 'juan_perez'
    },
    {
      id: 2,
      username: 'ana_gomez'
    },
    {
      id: 3,
      username: 'pepito_funes'
    }
  ];
  
  private _hashtags: Hashtag[] = [
    {
      id: 1,
      descripcion: 'Playa'
    },
    {
      id: 2,
      descripcion: 'Comida'
    },
    {
      id: 3,
      descripcion: 'Pesca'
    }
  ];

  private _publicaciones: Publicacion[] = [
    {
      id: 1,
      imagen: 'https://upload.wikimedia.org/wikipedia/commons/9/96/Barbados_beach.jpg',
      descripcion: 'Una playa hermosa',
      usuario: this._usuarios[0],
      hashtag: [this._hashtags[0]]
    },
    {
      id: 2,
      imagen: 'https://hips.hearstapps.com/hmg-prod/images/ghk090121savorfeature-005-1658424349.jpg?crop=0.974xw:0.652xh;0.0256xw,0.166xh&resize=640:*',
      descripcion: 'Un almuerzo épico',
      usuario: this._usuarios[1],
      hashtag: [this._hashtags[1]]
    },
    {
      id: 3,
      imagen: 'https://upload.wikimedia.org/wikipedia/commons/d/d5/Recreational-fishing-colombia.jpg',
      descripcion: 'Una experiencia de pesca',
      usuario: this._usuarios[2],
      hashtag: [this._hashtags[2]]
    }
  ];

  private _likes: Like[] = [
    {
      usuario: this._usuarios[0],
      publicacion: this._publicaciones[1]
    },
    {
      usuario: this._usuarios[1],
      publicacion: this._publicaciones[0]
    },
    {
      usuario: this._usuarios[2],
      publicacion: this._publicaciones[1]
    },
  ]

  get publicaciones(): Publicacion[] {
    return this._publicaciones;
  }

  get usuarios(): Usuario[] {
    return this._usuarios;
  }

  get likes(): Like[] {
    return this._likes;
  }
}
