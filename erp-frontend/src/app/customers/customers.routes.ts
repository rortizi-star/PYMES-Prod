import { Routes } from '@angular/router';
import { CustomerListComponent } from './customer-list/customer-list.component';
import { CustomerFormComponent } from './customer-form/customer-form.component';

export const customersRoutes: Routes = [
  { path: '', component: CustomerListComponent },
  { path: 'new', component: CustomerFormComponent },
  { path: ':id/edit', component: CustomerFormComponent }
];
