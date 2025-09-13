// ...
import { Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';

import { MainLayoutComponent } from './layout/main-layout/main-layout.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'dashboard', loadChildren: () => import('./dashboard/dashboard.module').then(m => m.default) },
      { path: 'users', loadChildren: () => import('./users/users.routes').then(m => m.usersRoutes) },
      { path: 'products', loadChildren: () => import('./products/products.routes').then(m => m.productsRoutes) },
      { path: 'customers', loadChildren: () => import('./customers/customers.routes').then(m => m.customersRoutes) },
      { path: 'accounts', loadChildren: () => import('./accounts/accounts.routes').then(m => m.accountsRoutes) },
      { path: 'cash-registers', loadChildren: () => import('./cash-registers/cash-registers.routes').then(m => m.cashRegistersRoutes) },
      { path: 'roles', loadChildren: () => import('./roles/roles.routes').then(m => m.rolesRoutes) },
      { path: 'invoices', loadChildren: () => import('./invoices/invoices.routes').then(m => m.invoicesRoutes) },
      { path: 'purchases', loadChildren: () => import('./purchases/purchases.routes').then(m => m.purchasesRoutes) },
      { path: 'suppliers', loadChildren: () => import('./suppliers/suppliers.routes').then(m => m.suppliersRoutes) },
    { path: 'banks', loadChildren: () => import('./banks/banks.routes').then(m => m.banksRoutes) },
    { path: 'financial-reports', loadChildren: () => import('./financial-reports/financial-reports.module').then(m => m.FinancialReportsModule) }
    ]
  }
];
