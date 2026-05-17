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
  selector: 'app-season-detail',
  templateUrl: './season-detail.component.html',
  styleUrls: ['./season-detail.component.css']
})
export class SeasonDetailComponent implements OnInit {
  serie: Serie | null = null;
  season: Temporada | null = null;
  loading = true;
  error = '';
  infoMessage = '';
  watchedChapters = new Set<string>();
  expandedChapters = new Set<number>();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private serieService: SerieService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const serieId = Number(params.get('id'));
      const seasonNumber = Number(params.get('season'));
      this.loading = true;
      this.error = '';
      this.serie = null;
      this.season = null;
      this.watchedChapters.clear();
      this.infoMessage = '';

      if (isNaN(serieId) || isNaN(seasonNumber)) {
        this.error = 'Parámetros de serie o temporada inválidos.';
        this.loading = false;
        return;
      }

      forkJoin({
        serie: this.serieService.getSerie(serieId),
        user: this.userService.getCurrentUser()
      }).subscribe({
        next: ({ serie, user }) => {
          this.serie = serie;
          this.sortSerieData(serie);
          this.season = serie.temporadas?.find(item => item.numeroTemporada === seasonNumber) || null;
          if (!this.season) {
            this.error = 'No se ha encontrado la temporada solicitada.';
          } else {
            this.setWatchedChapters(user);
          }
          this.loading = false;
        },
        error: err => {
          this.error = err;
          this.loading = false;
        }
      });
    });
  }

  backToSerie(): void {
    if (!this.serie?.idSerie) {
      this.router.navigate(['/']);
      return;
    }
    this.router.navigate(['/serie', this.serie.idSerie]);
  }

  markAsWatched(capitulo: Capitulo): void {
    if (!this.serie || !this.season || capitulo.numeroCapitulo === undefined) {
      console.error('Error: Missing required data', { serie: this.serie, season: this.season, capitulo });
      return;
    }
    this.serieService.markChapterWatched(this.serie.idSerie, this.season.numeroTemporada, capitulo.numeroCapitulo)
      .subscribe({
        next: () => {
          const key = `${this.season?.numeroTemporada}-${capitulo.numeroCapitulo}`;
          this.watchedChapters.add(key);
          const isLastEpisode = this.isLastChapter(capitulo);
          this.infoMessage = isLastEpisode
            ? `¡Has completado la serie "${this.serie?.nombreSerie}"! Se ha movido a terminadas.`
            : `Capítulo "${capitulo.nombreCapitulo}" marcado como visto.`;
        },
        error: err => {
          this.error = err;
        }
      });
  }

  private isLastChapter(capitulo: Capitulo): boolean {
    if (!this.serie || !this.season || !this.serie.temporadas) {
      return false;
    }
    const lastSeason = [...this.serie.temporadas].sort((a, b) => a.numeroTemporada - b.numeroTemporada).pop();
    if (!lastSeason || lastSeason.numeroTemporada !== this.season.numeroTemporada) {
      return false;
    }
    const lastEpisode = lastSeason.capitulos ? [...lastSeason.capitulos].sort((a, b) => a.numeroCapitulo - b.numeroCapitulo).pop() : undefined;
    return lastEpisode?.numeroCapitulo === capitulo.numeroCapitulo;
  }

  toggleDescription(capituloId: number): void {
    if (this.expandedChapters.has(capituloId)) {
      this.expandedChapters.delete(capituloId);
    } else {
      this.expandedChapters.add(capituloId);
    }
  }

  isDescriptionExpanded(capituloId: number): boolean {
    return this.expandedChapters.has(capituloId);
  }

  hasWatched(capitulo: Capitulo): boolean {
    return this.season ? this.watchedChapters.has(`${this.season.numeroTemporada}-${capitulo.numeroCapitulo}`) : false;
  }

  private setWatchedChapters(user: Usuario): void {
    this.watchedChapters.clear();
    const watchedIds = new Set<number>(
      (user.capitulosVistos || [])
        .map(cap => cap.idCapitulo)
        .filter((id): id is number => id !== undefined)
    );
    const season = this.season;
    if (!season) {
      return;
    }
    season.capitulos?.forEach(capitulo => {
      if (capitulo.idCapitulo && watchedIds.has(capitulo.idCapitulo)) {
        this.watchedChapters.add(`${season.numeroTemporada}-${capitulo.numeroCapitulo}`);
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
