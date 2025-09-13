import { Routes } from '@angular/router';
import { AccountListComponent } from './account-list.component';
import { AccountFormComponent } from './account-form.component';
export const accountsRoutes: Routes = [
  { path: '', component: AccountListComponent },
  { path: 'new', component: AccountFormComponent },
  { path: ':id/edit', component: AccountFormComponent }
];
