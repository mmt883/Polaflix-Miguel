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
  selector: 'app-detalle-temporada',
  templateUrl: './detalle-temporada.component.html',
  styleUrls: ['./detalle-temporada.component.css']
})
export class DetalleTemporadaComponent implements OnInit {
  serie: Serie | null = null;
  temporada: Temporada | null = null;
  loading = true;
  error = '';
  mensajeInfo = '';
  capitulosVistos = new Set<string>();
  capitulosExpandidos = new Set<number>();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private serieService: SerieService,
    private usuarioService: UserService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const serieId = Number(params.get('id'));
      const numeroTemporada = Number(params.get('season'));
      this.loading = true;
      this.error = '';
      this.serie = null;
      this.temporada = null;
      this.capitulosVistos.clear();
      this.mensajeInfo = '';

      if (isNaN(serieId) || isNaN(numeroTemporada)) {
        this.error = 'Parámetros de serie o temporada inválidos.';
        this.loading = false;
        return;
      }

      forkJoin({
        serie: this.serieService.getSerie(serieId),
        usuario: this.usuarioService.getCurrentUser()
      }).subscribe({
        next: ({ serie, usuario }) => {
          this.serie = serie;
          this.ordenarDatosSerie(serie);
          this.temporada = serie.temporadas?.find(item => item.numeroTemporada === numeroTemporada) || null;
          if (!this.temporada) {
            this.error = 'No se ha encontrado la temporada solicitada.';
          } else {
            this.establecerCapitulosVistos(usuario);
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

  volverASerie(): void {
    if (!this.serie?.idSerie) {
      this.router.navigate(['/']);
      return;
    }
    this.router.navigate(['/serie', this.serie.idSerie]);
  }

  marcarComoVisto(capitulo: Capitulo): void {
    if (!this.serie || !this.temporada || capitulo.numeroCapitulo === undefined) {
      console.error('Error: Datos requeridos faltantes', { serie: this.serie, temporada: this.temporada, capitulo });
      return;
    }
    this.serieService.markChapterWatched(this.serie.idSerie, this.temporada.numeroTemporada, capitulo.numeroCapitulo)
      .subscribe({
        next: () => {
          const clave = `${this.temporada?.numeroTemporada}-${capitulo.numeroCapitulo}`;
          this.capitulosVistos.add(clave);
          const esUltimoEpisodio = this.esUltimoCapitulo(capitulo);
          this.mensajeInfo = esUltimoEpisodio
            ? `¡Has completado la serie "${this.serie?.nombreSerie}"! Se ha movido a terminadas.`
            : `Capítulo "${capitulo.nombreCapitulo}" marcado como visto.`;
        },
        error: (err: any) => {
          this.error = (err instanceof Error) ? err.message : String(err);
        }
      });
  }

  private esUltimoCapitulo(capitulo: Capitulo): boolean {
    if (!this.serie || !this.temporada || !this.serie.temporadas) {
      return false;
    }
    const ultimaTemporada = [...this.serie.temporadas].sort((a, b) => a.numeroTemporada - b.numeroTemporada).pop();
    if (!ultimaTemporada || ultimaTemporada.numeroTemporada !== this.temporada.numeroTemporada) {
      return false;
    }
    const ultimoEpisodio = ultimaTemporada.capitulos ? [...ultimaTemporada.capitulos].sort((a, b) => a.numeroCapitulo - b.numeroCapitulo).pop() : undefined;
    return ultimoEpisodio?.numeroCapitulo === capitulo.numeroCapitulo;
  }

  alternarDescripcion(capituloId: number): void {
    if (this.capitulosExpandidos.has(capituloId)) {
      this.capitulosExpandidos.delete(capituloId);
    } else {
      this.capitulosExpandidos.add(capituloId);
    }
  }

  estaDescripcionExpandida(capituloId: number): boolean {
    return this.capitulosExpandidos.has(capituloId);
  }

  haVisto(capitulo: Capitulo): boolean {
    return this.temporada ? this.capitulosVistos.has(`${this.temporada.numeroTemporada}-${capitulo.numeroCapitulo}`) : false;
  }

  private establecerCapitulosVistos(usuario: Usuario): void {
    this.capitulosVistos.clear();
    const idsCapitulosVistos = new Set<number>(
      (usuario.capitulosVistos || [])
        .map(cap => cap.idCapitulo)
        .filter((id): id is number => id !== undefined)
    );
    const temporada = this.temporada;
    if (!temporada) {
      return;
    }
    temporada.capitulos?.forEach(capitulo => {
      if (capitulo.idCapitulo && idsCapitulosVistos.has(capitulo.idCapitulo)) {
        this.capitulosVistos.add(`${temporada.numeroTemporada}-${capitulo.numeroCapitulo}`);
      }
    });
  }

  private ordenarDatosSerie(serie: Serie): void {
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
