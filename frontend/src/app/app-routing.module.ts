import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { SerieDetailComponent } from './serie-detail/serie-detail.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'serie/:id', component: SerieDetailComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'enabled' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
