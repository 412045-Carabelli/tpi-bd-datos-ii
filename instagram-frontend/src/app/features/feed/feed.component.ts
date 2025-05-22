import { Component } from '@angular/core';
import { Publicacion } from '../../models/publicacion.model';
import { ApiService } from '../../services/api.service';
import { DataService } from '../../services/data.service';

@Component({
  selector: 'app-feed',
  imports: [],
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent {
  publicaciones: Publicacion[] = [];

  constructor(private data: DataService) {}

  ngOnInit(): void {
    this.publicaciones = this.data.publicaciones;
  }
}
