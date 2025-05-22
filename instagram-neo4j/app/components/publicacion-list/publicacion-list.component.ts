import { Component, type OnInit } from "@angular/core"
import type { Neo4jService } from "../../services/neo4j.service"
import type { Publicacion } from "../../models/models"

@Component({
  selector: "app-publicacion-list",
  templateUrl: "./publicacion-list.component.html",
  styleUrls: ["./publicacion-list.component.css"],
})
export class PublicacionListComponent implements OnInit {
  publicaciones: Publicacion[] = []
  loading = true
  error: string | null = null
  usuarioActual = "1" // Simulando un usuario logueado

  constructor(private neo4jService: Neo4jService) {}

  ngOnInit(): void {
    this.loadPublicaciones()
  }

  loadPublicaciones(): void {
    this.loading = true
    this.neo4jService.getPublicaciones().subscribe({
      next: (data) => {
        this.publicaciones = data
        this.loading = false
      },
      error: (err) => {
        this.error = "Error al cargar publicaciones: " + err.message
        this.loading = false
      },
    })
  }

  likePublicacion(idPublicacion: string): void {
    this.neo4jService.likePublicacion(idPublicacion, this.usuarioActual).subscribe({
      next: () => {
        // Actualizar la UI
        const publicacion = this.publicaciones.find((p) => p.id_publicacion === idPublicacion)
        if (publicacion && publicacion.likes !== undefined) {
          publicacion.likes += 1
        }
      },
      error: (err) => {
        this.error = "Error al dar like: " + err.message
      },
    })
  }

  deletePublicacion(idPublicacion: string): void {
    if (confirm("¿Estás seguro de eliminar esta publicación?")) {
      this.neo4jService.deletePublicacion(idPublicacion).subscribe({
        next: () => {
          this.publicaciones = this.publicaciones.filter((p) => p.id_publicacion !== idPublicacion)
        },
        error: (err) => {
          this.error = "Error al eliminar publicación: " + err.message
        },
      })
    }
  }
}
