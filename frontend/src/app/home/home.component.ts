import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Serie } from '../models/serie.model';
import { Usuario } from '../models/usuario.model';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  user: Usuario | null = null;
  loading = true;
  error = '';

  constructor(
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe({
      next: user => {
        this.user = {
          ...user,
          seriesPendientes: user.seriesPendientes || [],
          seriesEmpezadas: user.seriesEmpezadas || [],
          seriesTerminadas: user.seriesTerminadas || []
        };
        this.loading = false;
      },
      error: err => {
        this.error = (err instanceof Error) ? err.message : String(err);
        this.loading = false;
      }
    });
  }

  getUsername(): string {
    return this.user?.nombreUsuario || this.userService.getUsername();
  }

  openSerie(id: number | undefined): void {
    if (!id) {
      this.error = 'No se ha encontrado la serie solicitada.';
      return;
    }
    this.router.navigate(['/serie', id]);
  }
}
