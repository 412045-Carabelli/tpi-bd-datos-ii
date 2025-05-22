import { NgModule } from "@angular/core"
import { RouterModule, type Routes } from "@angular/router"
import { PublicacionListComponent } from "./components/publicacion-list/publicacion-list.component"
import { PublicacionFormComponent } from "./components/publicacion-form/publicacion-form.component"
import { PublicacionDetailComponent } from "./components/publicacion-detail/publicacion-detail.component"

const routes: Routes = [
  { path: "", redirectTo: "/publicaciones", pathMatch: "full" },
  { path: "publicaciones", component: PublicacionListComponent },
  { path: "publicaciones/new", component: PublicacionFormComponent },
  { path: "publicaciones/edit/:id", component: PublicacionFormComponent },
  { path: "publicaciones/:id", component: PublicacionDetailComponent },
  { path: "**", redirectTo: "/publicaciones" },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
