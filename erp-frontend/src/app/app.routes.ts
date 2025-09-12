import { Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'dashboard',
    loadChildren: () => import('./dashboard/dashboard.module').then(m => m.default),
    canActivate: [AuthGuard]
  },
  // Agrega aquí los demás módulos protegidos, por ejemplo:
  // {
  //   path: 'users',
  //   loadChildren: () => import('./users/users.module').then(m => m.UsersModule),
  //   canActivate: [AuthGuard]
  // },
  // ...
];
