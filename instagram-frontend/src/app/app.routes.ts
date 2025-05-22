import { Routes } from '@angular/router';
import { PublicacionComponent } from './features/publicacion/publicacion.component';
import { UsuarioComponent } from './features/usuario/usuario.component';
import { LikeComponent } from './features/like/like.component';

export const routes: Routes = [
    { path: '', redirectTo: 'feed', pathMatch: 'full' },
    { path: 'publicacion/:publiId', component: PublicacionComponent },
    { path: 'perfil/:userId', component: UsuarioComponent },
    { path: 'likes/:publiId', component: LikeComponent }
];
