import { Component, OnInit } from '@angular/core';
import { ApiService } from './services/api.service';
import { Publicacion } from './models/publicacion.model';
import { Usuario } from './models/usuario.model';
import { Hashtag } from './models/hashtag.model';

@Component({
  selector: 'app-root',
  template: `<h1>Mini Instagram</h1>`
})
export class AppComponent implements OnInit {
  usuarios: Usuario[] = [
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
  
  hashtags: Hashtag[] = [
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

  publicaciones: Publicacion[] = [
    {
      id: 1,
      imagen: 'https://upload.wikimedia.org/wikipedia/commons/9/96/Barbados_beach.jpg',
      descripcion: 'Una playa hermosa',
      usuario: this.usuarios[0],
      hashtag: [this.hashtags[0]]
    },
    {
      id: 2,
      imagen: 'https://hips.hearstapps.com/hmg-prod/images/ghk090121savorfeature-005-1658424349.jpg?crop=0.974xw:0.652xh;0.0256xw,0.166xh&resize=640:*',
      descripcion: 'Un almuerzo épico',
      usuario: this.usuarios[1],
      hashtag: [this.hashtags[1]]
    },
    {
      id: 3,
      imagen: 'https://upload.wikimedia.org/wikipedia/commons/d/d5/Recreational-fishing-colombia.jpg',
      descripcion: 'Una experiencia de pesca',
      usuario: this.usuarios[2],
      hashtag: [this.hashtags[2]]
    }
  ];
  
  constructor(private api: ApiService) {}
  
  ngOnInit(): void {
  }

}
