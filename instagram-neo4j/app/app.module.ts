import { NgModule } from "@angular/core"
import { BrowserModule } from "@angular/platform-browser"
import { HttpClientModule } from "@angular/common/http"
import { ReactiveFormsModule } from "@angular/forms"

import { AppRoutingModule } from "./app-routing.module"
import { AppComponent } from "./app.component"
import { PublicacionListComponent } from "./components/publicacion-list/publicacion-list.component"
import { PublicacionFormComponent } from "./components/publicacion-form/publicacion-form.component"
import { PublicacionDetailComponent } from "./components/publicacion-detail/publicacion-detail.component"

@NgModule({
  declarations: [AppComponent, PublicacionListComponent, PublicacionFormComponent, PublicacionDetailComponent],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, ReactiveFormsModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
