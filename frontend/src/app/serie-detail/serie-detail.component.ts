import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { Serie } from '../models/serie.model';
import { Temporada } from '../models/temporada.model';
import { Capitulo } from '../models/capitulo.model';
import { Usuario } from '../models/usuario.model';
import { SerieService } from '../services/serie.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-serie-detail',
  templateUrl: './serie-detail.component.html',
  styleUrls: ['./serie-detail.component.css']
})
export class SerieDetailComponent implements OnInit {
  serie: Serie | null = null;
  currentSeasonIndex = 0;
  loading = true;
  error = '';
  infoMessage = '';
  watchedChapters = new Set<number>();
  expandedChapters = new Set<number>();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private serieService: SerieService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      this.loading = true;
      this.error = '';
      this.serie = null;
      this.currentSeasonIndex = 0;
      this.watchedChapters.clear();

      if (isNaN(id)) {
        this.error = 'ID de serie inválido.';
        this.loading = false;
        return;
      }

      forkJoin({
        serie: this.serieService.getSerie(id),
        user: this.userService.getCurrentUser()
      }).subscribe({
        next: ({ serie, user }) => {
          this.serie = serie;
          if (this.serie) {
            this.sortSerieData(this.serie);
            this.loadWatchedChapters(user);
          }
          this.loading = false;
        },
        error: err => {
          this.error = (err instanceof Error) ? err.message : String(err);
          this.loading = false;
        }
      });
    });
  }

  backToHome(): void {
    this.router.navigate(['/']);
  }

  get currentSeason(): Temporada | null {
    if (!this.serie?.temporadas || this.serie.temporadas.length === 0) {
      return null;
    }
    return this.serie.temporadas[this.currentSeasonIndex] || null;
  }

  previousSeason(): void {
    if (this.currentSeasonIndex > 0) {
      this.currentSeasonIndex--;
      this.expandedChapters.clear();
    }
  }

  nextSeason(): void {
    if (this.serie && this.serie.temporadas && this.currentSeasonIndex < this.serie.temporadas.length - 1) {
      this.currentSeasonIndex++;
      this.expandedChapters.clear();
    }
  }

  toggleDescription(capituloId: number | undefined): void {
    if (!capituloId) return;
    if (this.expandedChapters.has(capituloId)) {
      this.expandedChapters.delete(capituloId);
    } else {
      this.expandedChapters.add(capituloId);
    }
  }

  isDescriptionExpanded(capituloId: number | undefined): boolean {
    return capituloId ? this.expandedChapters.has(capituloId) : false;
  }

  hasWatched(capituloId: number | undefined): boolean {
    return capituloId ? this.watchedChapters.has(capituloId) : false;
  }

  markAsWatched(capitulo: Capitulo): void {
    if (!this.serie?.idSerie || !this.currentSeason || !capitulo.idCapitulo) {
      this.error = 'No se pueden marcar los datos del capítulo.';
      return;
    }

    this.serieService.markChapterWatched(this.serie.idSerie, this.currentSeason.numeroTemporada, capitulo.numeroCapitulo)
      .subscribe({
        next: () => {
          this.watchedChapters.add(capitulo.idCapitulo!);
          this.infoMessage = `Capítulo "${capitulo.nombreCapitulo}" marcado como visto.`;
          setTimeout(() => this.infoMessage = '', 1000);
        },
        error: err => {
          this.error = (err instanceof Error) ? err.message : String(err);
        }
      });
  }

  private loadWatchedChapters(user: Usuario): void {
    this.watchedChapters.clear();
    (user.capitulosVistos || []).forEach(cap => {
      if (cap.idCapitulo !== undefined) {
        this.watchedChapters.add(cap.idCapitulo);
      }
    });
  }

  private sortSerieData(serie: Serie): void {
    if (serie.temporadas) {
      serie.temporadas.sort((a, b) => a.numeroTemporada - b.numeroTemporada);
      serie.temporadas.forEach(temporada => {
        if (temporada.capitulos) {
          temporada.capitulos.sort((a, b) => a.numeroCapitulo - b.numeroCapitulo);
        }
      });
    }
  }
}
