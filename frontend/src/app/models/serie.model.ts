import { Temporada } from './temporada.model';

export interface Serie {
  idSerie?: number;
  nombreSerie: string;
  descripcion?: string;
  categoria?: string;
  temporadas?: Temporada[];
}
