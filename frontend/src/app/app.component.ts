import { Component } from '@angular/core';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(public userService: UserService) {}

  throwNotImplemented(event: Event, feature: string): void {
    event.preventDefault();
    throw new Error(`La función "${feature}" no está implementada.`);
  }
}

