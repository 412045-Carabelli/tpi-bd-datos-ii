export interface Usuario {
  id_usuario: string
  nom_usuario: string
  seguidores: number
  seguidos: number
}

export interface Publicacion {
  id_publicacion?: string
  imagen: string
  descripcion: string
  id_usuario: string
  id_hashtag?: string[]
  fecha_creacion?: Date
  likes?: number
}

export interface Hashtag {
  id_hashtag: string
  descripcion: string
}

export interface Like {
  id_publicacion: string
  id_usuario: string
}
