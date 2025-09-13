import { Routes } from '@angular/router';
import { InvoiceListComponent } from './invoice-list/invoice-list.component';
import { InvoiceFormComponent } from './invoice-form/invoice-form.component';

export const invoicesRoutes: Routes = [
  { path: '', component: InvoiceListComponent },
  { path: 'nuevo', component: InvoiceFormComponent },
  { path: 'editar/:id', component: InvoiceFormComponent }
];
