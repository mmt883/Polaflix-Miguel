import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { Usuario } from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly username = 'John Nieve';
  private readonly userId = 1;
  private readonly baseUrl = `${environment.apiUrl}/usuarios`;

  constructor(private http: HttpClient) { }

  getUsername(): string {
    return this.username;
  }

  getUserId(): number {
    return this.userId;
  }

  getCurrentUser(): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.baseUrl}/${this.userId}`).pipe(
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
      return throwError(() => 'No se ha encontrado el usuario solicitado.');
    }
    return throwError(() => `Error ${error.status}: ${error.message}`);
  }
}
