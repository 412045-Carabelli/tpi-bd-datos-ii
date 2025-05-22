import { Publicacion } from "./publicacion.model";
import { Usuario } from "./usuario.model";

export interface Like {
    usuario: Usuario;
    publicacion: Publicacion;
}
