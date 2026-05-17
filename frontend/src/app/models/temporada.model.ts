import { Capitulo } from './capitulo.model';

export interface Temporada {
  idTemporada?: number;
  nombreTemporada: string;
  numeroTemporada: number;
  descripcion?: string;
  capitulos: Capitulo[];
}
