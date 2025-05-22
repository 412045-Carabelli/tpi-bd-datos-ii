import { Component, type OnInit } from "@angular/core"
import { type FormBuilder, type FormGroup, Validators, type FormArray } from "@angular/forms"
import type { ActivatedRoute, Router } from "@angular/router"
import type { Neo4jService } from "../../services/neo4j.service"
import type { Publicacion, Hashtag } from "../../models/models"

@Component({
  selector: "app-publicacion-form",
  templateUrl: "./publicacion-form.component.html",
  styleUrls: ["./publicacion-form.component.css"],
})
export class PublicacionFormComponent implements OnInit {
  publicacionForm!: FormGroup
  isEditMode = false
  publicacionId: string | null = null
  hashtags: Hashtag[] = []
  loading = false
  error: string | null = null
  imagePreview: string | null = null
  usuarioActual = "1" // Simulando un usuario logueado

  constructor(
    private fb: FormBuilder,
    private neo4jService: Neo4jService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.initForm()
    this.loadHashtags()

    this.publicacionId = this.route.snapshot.paramMap.get("id")
    if (this.publicacionId) {
      this.isEditMode = true
      this.loadPublicacion(this.publicacionId)
    }
  }

  initForm(): void {
    this.publicacionForm = this.fb.group({
      imagen: ["", Validators.required],
      descripcion: ["", Validators.required],
      hashtags: this.fb.array([]),
    })
  }

  loadHashtags(): void {
    this.neo4jService.getHashtags().subscribe({
      next: (data) => {
        this.hashtags = data
      },
      error: (err) => {
        this.error = "Error al cargar hashtags: " + err.message
      },
    })
  }

  loadPublicacion(id: string): void {
    this.loading = true
    this.neo4jService.getPublicacionById(id).subscribe({
      next: (publicacion) => {
        this.publicacionForm.patchValue({
          imagen: publicacion.imagen,
          descripcion: publicacion.descripcion,
        })

        this.imagePreview = publicacion.imagen

        // Cargar hashtags
        if (publicacion.id_hashtag && publicacion.id_hashtag.length) {
          const hashtagsArray = this.publicacionForm.get("hashtags") as FormArray
          publicacion.id_hashtag.forEach((hashtagId) => {
            hashtagsArray.push(this.fb.control(hashtagId))
          })
        }

        this.loading = false
      },
      error: (err) => {
        this.error = "Error al cargar la publicación: " + err.message
        this.loading = false
      },
    })
  }

  get hashtagsArray(): FormArray {
    return this.publicacionForm.get("hashtags") as FormArray
  }

  onHashtagChange(event: any, hashtagId: string): void {
    const hashtagsArray = this.publicacionForm.get("hashtags") as FormArray

    if (event.target.checked) {
      hashtagsArray.push(this.fb.control(hashtagId))
    } else {
      const index = hashtagsArray.controls.findIndex((control) => control.value === hashtagId)
      if (index !== -1) {
        hashtagsArray.removeAt(index)
      }
    }
  }

  isHashtagSelected(hashtagId: string): boolean {
    const hashtagsArray = this.publicacionForm.get("hashtags") as FormArray
    return hashtagsArray.controls.some((control) => control.value === hashtagId)
  }

  onImageChange(event: any): void {
    const file = event.target.files[0]
    if (file) {
      // En una aplicación real, aquí subirías la imagen a un servidor
      // y obtendrías la URL. Para este ejemplo, usamos FileReader para preview
      const reader = new FileReader()
      reader.onload = () => {
        this.imagePreview = reader.result as string
        this.publicacionForm.patchValue({
          imagen: this.imagePreview,
        })
      }
      reader.readAsDataURL(file)
    }
  }

  onSubmit(): void {
    if (this.publicacionForm.invalid) {
      return
    }

    this.loading = true

    const publicacionData: Publicacion = {
      imagen: this.publicacionForm.value.imagen,
      descripcion: this.publicacionForm.value.descripcion,
      id_usuario: this.usuarioActual,
      id_hashtag: this.publicacionForm.value.hashtags,
    }

    if (this.isEditMode && this.publicacionId) {
      this.neo4jService.updatePublicacion(this.publicacionId, publicacionData).subscribe({
        next: () => {
          this.router.navigate(["/publicaciones"])
        },
        error: (err) => {
          this.error = "Error al actualizar la publicación: " + err.message
          this.loading = false
        },
      })
    } else {
      this.neo4jService.createPublicacion(publicacionData).subscribe({
        next: () => {
          this.router.navigate(["/publicaciones"])
        },
        error: (err) => {
          this.error = "Error al crear la publicación: " + err.message
          this.loading = false
        },
      })
    }
  }
}
