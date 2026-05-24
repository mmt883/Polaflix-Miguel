import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { Serie } from '../models/serie.model';
import { UserService } from './user.service';
import { Usuario } from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class SerieService {
  private baseUrl = `${environment.apiUrl}/series`;

  constructor(
    private http: HttpClient,
    private userService: UserService
  ) { }

  getSeries(): Observable<Serie[]> {
    return this.http.get<Serie[]>(this.baseUrl).pipe(
      catchError(this.handleError)
    );
  }

  getSerie(id: number): Observable<Serie> {
    return this.http.get<Serie>(`${this.baseUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  addSeriePendiente(usuarioId: number, serieId: number | undefined): Observable<Usuario> {
    if (!serieId) {
      return throwError(() => 'Serie inválida.');
    }
    const url = `${environment.apiUrl}/usuarios/${usuarioId}/series-pendientes/${serieId}`;
    return this.http.post<Usuario>(url, {}).pipe(
      catchError(this.handleError)
    );
  }

  markChapterWatched(serieId: number | undefined, numTemporada: number, numCapitulo: number): Observable<void> {
    if (!serieId) {
      return throwError(() => 'La serie no es válida.');
    }
    const userId = this.userService.getUserId();
    const url = `${environment.apiUrl}/usuarios/${userId}/registros/${serieId}/temporadas/${numTemporada}/capitulos/${numCapitulo}`;
    return this.http.put<void>(url, {}).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      return throwError(() => `Error de red: ${error.error.message}`);
    }
    if (error.status === 0) {
      return throwError(() => 'No se puede conectar con el servidor. Asegúrate de que el backend está en funcionamiento.');
    }
    if (error.status === 404) {
      return throwError(() => 'No se ha encontrado el recurso solicitado.');
    }
    return throwError(() => `Error ${error.status}: ${error.message}`);
  }
}
