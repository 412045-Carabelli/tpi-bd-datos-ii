import { Component, type OnInit } from "@angular/core"
import type { ActivatedRoute, Router } from "@angular/router"
import type { Neo4jService } from "../../services/neo4j.service"
import type { Publicacion, Usuario, Hashtag } from "../../models/models"

@Component({
  selector: "app-publicacion-detail",
  templateUrl: "./publicacion-detail.component.html",
  styleUrls: ["./publicacion-detail.component.css"],
})
export class PublicacionDetailComponent implements OnInit {
  publicacion: Publicacion | null = null
  usuario: Usuario | null = null
  hashtags: Hashtag[] = []
  loading = true
  error: string | null = null
  usuarioActual = "1" // Simulando un usuario logueado

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private neo4jService: Neo4jService,
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get("id")
    if (id) {
      this.loadPublicacion(id)
    } else {
      this.error = "ID de publicación no proporcionado"
      this.loading = false
    }
  }

  loadPublicacion(id: string): void {
    this.neo4jService.getPublicacionById(id).subscribe({
      next: (publicacion) => {
        this.publicacion = publicacion

        // Cargar información del usuario
        if (publicacion.id_usuario) {
          this.loadUsuario(publicacion.id_usuario)
        }

        // Cargar información de hashtags
        if (publicacion.id_hashtag && publicacion.id_hashtag.length) {
          this.loadHashtags()
        } else {
          this.loading = false
        }
      },
      error: (err) => {
        this.error = "Error al cargar la publicación: " + err.message
        this.loading = false
      },
    })
  }

  loadUsuario(idUsuario: string): void {
    this.neo4jService.getUsuarios().subscribe({
      next: (usuarios) => {
        this.usuario = usuarios.find((u) => u.id_usuario === idUsuario) || null
      },
      error: (err) => {
        this.error = "Error al cargar información del usuario: " + err.message
      },
    })
  }

  loadHashtags(): void {
    this.neo4jService.getHashtags().subscribe({
      next: (hashtags) => {
        if (this.publicacion && this.publicacion.id_hashtag) {
          this.hashtags = hashtags.filter((h) => this.publicacion?.id_hashtag?.includes(h.id_hashtag))
        }
        this.loading = false
      },
      error: (err) => {
        this.error = "Error al cargar hashtags: " + err.message
        this.loading = false
      },
    })
  }

  likePublicacion(): void {
    if (!this.publicacion || !this.publicacion.id_publicacion) return

    this.neo4jService.likePublicacion(this.publicacion.id_publicacion, this.usuarioActual).subscribe({
      next: () => {
        if (this.publicacion && this.publicacion.likes !== undefined) {
          this.publicacion.likes += 1
        }
      },
      error: (err) => {
        this.error = "Error al dar like: " + err.message
      },
    })
  }

  deletePublicacion(): void {
    if (!this.publicacion || !this.publicacion.id_publicacion) return

    if (confirm("¿Estás seguro de eliminar esta publicación?")) {
      this.neo4jService.deletePublicacion(this.publicacion.id_publicacion).subscribe({
        next: () => {
          this.router.navigate(["/publicaciones"])
        },
        error: (err) => {
          this.error = "Error al eliminar publicación: " + err.message
        },
      })
    }
  }
}
