import { Routes } from '@angular/router';
import { CashRegisterListComponent } from './cash-register-list/cash-register-list.component';
import { CashRegisterFormComponent } from './cash-register-form/cash-register-form.component';

export const cashRegistersRoutes: Routes = [
  { path: '', component: CashRegisterListComponent },
  { path: 'nuevo', component: CashRegisterFormComponent },
  { path: 'editar/:id', component: CashRegisterFormComponent }
];
