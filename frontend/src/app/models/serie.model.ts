import { Temporada } from './temporada.model';
import { Persona } from './persona.model';

export interface Serie {
  idSerie?: number;
  nombreSerie: string;
  descripcion?: string;
  categoria?: string;
  temporadas?: Temporada[];
  actores?: Persona[];
  creadores?: Persona[];
}
