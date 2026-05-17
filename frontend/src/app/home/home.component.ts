import { Component, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { Router } from '@angular/router';
import { Serie } from '../models/serie.model';
import { Usuario } from '../models/usuario.model';
import { SerieService } from '../services/serie.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  user: Usuario | null = null;
  allSeries: Serie[] = [];
  recommendations: Serie[] = [];
  loading = true;
  error = '';

  constructor(
    private serieService: SerieService,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    forkJoin({
      user: this.userService.getCurrentUser(),
      series: this.serieService.getSeries()
    }).subscribe({
      next: ({ user, series }) => {
        this.user = {
          ...user,
          seriesPendientes: user.seriesPendientes || [],
          seriesEmpezadas: user.seriesEmpezadas || [],
          seriesTerminadas: user.seriesTerminadas || []
        };
        this.allSeries = series;
        this.buildRecommendations();
        this.loading = false;
      },
      error: err => {
        this.error = err;
        this.loading = false;
      }
    });
  }

  private buildRecommendations(): void {
    const takenIds = new Set<number>();
    this.user?.seriesPendientes?.forEach(serie => serie.idSerie && takenIds.add(serie.idSerie));
    this.user?.seriesEmpezadas?.forEach(serie => serie.idSerie && takenIds.add(serie.idSerie));
    this.user?.seriesTerminadas?.forEach(serie => serie.idSerie && takenIds.add(serie.idSerie));

    this.recommendations = this.allSeries
      .filter(serie => serie.idSerie && !takenIds.has(serie.idSerie))
      .sort((a, b) => (a.nombreSerie || '').localeCompare(b.nombreSerie || ''));
  }

  getUsername(): string {
    return this.user?.nombreUsuario || this.userService.getUsername();
  }

  openSerie(id: number | undefined): void {
    if (!id) {
      return;
    }
    this.router.navigate(['/serie', id]);
  }

  addToPending(serie: Serie): void {
    if (!this.user || !serie.idSerie) {
      return;
    }
    this.serieService.addSeriePendiente(this.user.idUsuario, serie.idSerie).subscribe({
      next: () => {
        this.user!.seriesPendientes = [...(this.user?.seriesPendientes || []), serie];
        this.buildRecommendations();
      },
      error: err => {
        this.error = err;
      }
    });
  }
}
