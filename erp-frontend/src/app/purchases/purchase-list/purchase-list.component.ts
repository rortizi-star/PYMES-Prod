import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { DatePipe, CurrencyPipe, CommonModule } from '@angular/common';
import { PurchaseService } from '../purchase.service';

@Component({
  selector: 'app-purchase-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatIconModule, MatButtonModule, DatePipe, CurrencyPipe],
  templateUrl: './purchase-list.component.html',
  styleUrl: './purchase-list.component.scss'
})
export class PurchaseListComponent implements OnInit {
  purchases: any[] = [];
  displayedColumns: string[] = ['number', 'date', 'supplier', 'total', 'isActive', 'actions'];

  constructor(private purchaseService: PurchaseService, private router: Router) {}

  ngOnInit() {
    this.loadPurchases();
  }

  loadPurchases() {
    this.purchaseService.getPurchases().subscribe(data => this.purchases = data);
  }

  addPurchase() {
    this.router.navigate(['purchases', 'nuevo']);
  }

  editPurchase(purchase: any) {
    this.router.navigate(['purchases', 'editar', purchase.id]);
  }

  deletePurchase(purchase: any) {
    if (confirm('¿Seguro que deseas eliminar esta compra?')) {
      this.purchaseService.deletePurchase(purchase.id).subscribe(() => this.loadPurchases());
    }
  }
}
