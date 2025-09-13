import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { SupplierService } from '../supplier.service';

@Component({
  selector: 'app-supplier-list',
  standalone: true,
  imports: [MatTableModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './supplier-list.component.html',
  styleUrl: './supplier-list.component.scss'
})
export class SupplierListComponent implements OnInit {
  suppliers: any[] = [];
  displayedColumns: string[] = ['name', 'contact', 'phone', 'isActive', 'actions'];

  constructor(private supplierService: SupplierService, private router: Router) {}

  ngOnInit() {
    this.loadSuppliers();
  }

  loadSuppliers() {
    this.supplierService.getSuppliers().subscribe(data => this.suppliers = data);
  }

  addSupplier() {
    this.router.navigate(['suppliers', 'nuevo']);
  }

  editSupplier(supplier: any) {
    this.router.navigate(['suppliers', 'editar', supplier.id]);
  }

  deleteSupplier(supplier: any) {
    if (confirm('¿Seguro que deseas eliminar este proveedor?')) {
      this.supplierService.deleteSupplier(supplier.id).subscribe(() => this.loadSuppliers());
    }
  }
}
