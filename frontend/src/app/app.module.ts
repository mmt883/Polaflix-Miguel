import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { SerieDetailComponent } from './serie-detail/serie-detail.component';
import { SerieGroupComponent } from './serie-group/serie-group.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SerieDetailComponent,
    SerieGroupComponent
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
