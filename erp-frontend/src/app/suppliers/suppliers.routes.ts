import { Routes } from '@angular/router';
import { SupplierListComponent } from './supplier-list/supplier-list.component';
import { SupplierFormComponent } from './supplier-form/supplier-form.component';

export const suppliersRoutes: Routes = [
  { path: '', component: SupplierListComponent },
  { path: 'nuevo', component: SupplierFormComponent },
  { path: 'editar/:id', component: SupplierFormComponent }
];
