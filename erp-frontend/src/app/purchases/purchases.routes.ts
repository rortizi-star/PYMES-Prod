import { Routes } from '@angular/router';
import { PurchaseListComponent } from './purchase-list/purchase-list.component';
import { PurchaseFormComponent } from './purchase-form/purchase-form.component';

export const purchasesRoutes: Routes = [
  { path: '', component: PurchaseListComponent },
  { path: 'nuevo', component: PurchaseFormComponent },
  { path: 'editar/:id', component: PurchaseFormComponent }
];
