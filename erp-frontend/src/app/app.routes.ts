  {
    path: 'accounts',
    loadChildren: () => import('./accounts/accounts.routes').then(m => m.accountsRoutes),
    canActivate: [AuthGuard]
  },
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
  {
    path: 'users',
    loadChildren: () => import('./users/users.routes').then(m => m.usersRoutes),
    canActivate: [AuthGuard]
  },
  {
    path: 'products',
    loadChildren: () => import('./products/products.routes').then(m => m.productsRoutes),
    canActivate: [AuthGuard]
  },
  {
    path: 'customers',
    loadChildren: () => import('./customers/customers.routes').then(m => m.customersRoutes),
    canActivate: [AuthGuard]
  },
  {
    path: 'accounts',
    loadChildren: () => import('./accounts/accounts.routes').then(m => m.accountsRoutes),
    canActivate: [AuthGuard]
  }
];
