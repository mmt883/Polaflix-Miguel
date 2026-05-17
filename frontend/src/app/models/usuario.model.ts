import { Serie } from './serie.model';
import { Capitulo } from './capitulo.model';

export interface Usuario {
  idUsuario: number;
  nombreUsuario: string;
  contraseña?: string;
  cuentaBancaria?: string;
  tipo?: string;
  seriesPendientes?: Serie[];
  seriesEmpezadas?: Serie[];
  seriesTerminadas?: Serie[];
  capitulosVistos?: Capitulo[];
}
