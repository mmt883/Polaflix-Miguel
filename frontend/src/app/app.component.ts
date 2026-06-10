import { Component } from '@angular/core';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  errorMessage: string | null = null;
  private errorTimerId: number | null = null;

  constructor(public userService: UserService) {}

  throwNotImplemented(event: Event, feature: string): void {
    event.preventDefault();
    this.clearErrorTimer();
    this.errorMessage = `La función "${feature}" no está implementada.`;
    this.errorTimerId = window.setTimeout(() => {
      this.errorMessage = null;
      this.errorTimerId = null;
    }, 2000);
    throw new Error(this.errorMessage);
  }

  private clearErrorTimer(): void {
    if (this.errorTimerId !== null) {
      window.clearTimeout(this.errorTimerId);
      this.errorTimerId = null;
    }
  }
}

