import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Persona } from '../models/persona.model';
import { Serie } from '../models/serie.model';
import { SerieService } from '../services/serie.service';

@Component({
  selector: 'app-detalle-serie',
  templateUrl: './detalle-serie.component.html',
  styleUrls: ['./detalle-serie.component.css']
})
export class DetallSerieComponent implements OnInit {
  serie: Serie | null = null;
  loading = true;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private serieService: SerieService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      this.loading = true;
      this.error = '';
      this.serie = null;

      if (isNaN(id)) {
        this.error = 'ID de serie inválido.';
        this.loading = false;
        return;
      }

      this.serieService.getSerie(id).subscribe({
        next: value => {
          this.serie = value;
          if (this.serie) {
            this.sortSerieData(this.serie);
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

  abrirTemporada(temporadaNumero: number | undefined): void {
    if (!this.serie?.idSerie || temporadaNumero === undefined) {
      return;
    }
    this.router.navigate(['/serie', this.serie.idSerie, 'temporada', temporadaNumero]);
  }

  private sortSerieData(serie: Serie): void {
    if (serie.temporadas) {
      serie.temporadas.sort((a, b) => a.numeroTemporada - b.numeroTemporada);
    }
  }

  formatPersonas(personas: Persona[] | undefined): string {
    return personas?.map(persona => `${persona.nombre} ${persona.primerApellido}${persona.segundoApellido ? ' ' + persona.segundoApellido : ''}`).join(', ') || 'No especificados';
  }
}
