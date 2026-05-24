import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { DetallSerieComponent } from './detalle-serie/detalle-serie.component';
import { DetalleTemporadaComponent } from './detalle-temporada/detalle-temporada.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'serie/:id', component: DetallSerieComponent },
  { path: 'serie/:id/temporada/:season', component: DetalleTemporadaComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'enabled' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
