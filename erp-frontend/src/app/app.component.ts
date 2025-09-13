
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NgIf } from '@angular/common';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { AuthService } from './auth/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'erp-frontend';
  constructor(private auth: AuthService) {}
  isLoggedIn() {
    return this.auth.isLoggedIn();
  }
}
