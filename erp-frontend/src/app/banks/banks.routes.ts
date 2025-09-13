import { Routes } from '@angular/router';
import { BankListComponent } from './bank-list/bank-list.component';
import { BankFormComponent } from './bank-form/bank-form.component';

export const banksRoutes: Routes = [
  { path: '', component: BankListComponent },
  { path: 'new', component: BankFormComponent },
  { path: ':id/edit', component: BankFormComponent }
];
