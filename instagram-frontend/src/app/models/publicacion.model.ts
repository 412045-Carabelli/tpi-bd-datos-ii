import { Hashtag } from "./hashtag.model";
import { Usuario } from "./usuario.model";

export interface Publicacion {
    id: number;
    imagen: string;
    descripcion: string;
    usuario: Usuario;
    hashtag: Hashtag[];
}
