import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { DetallSerieComponent } from './detalle-serie/detalle-serie.component';
import { DetalleTemporadaComponent } from './detalle-temporada/detalle-temporada.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    DetallSerieComponent,
    DetalleTemporadaComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
