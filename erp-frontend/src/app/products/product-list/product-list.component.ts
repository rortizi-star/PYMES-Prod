import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { CurrencyPipe } from '@angular/common';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [MatTableModule, MatCardModule, MatIconModule, MatButtonModule, CurrencyPipe],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements OnInit {
  products: any[] = [];
  displayedColumns: string[] = ['name', 'code', 'price', 'isActive', 'actions'];

  constructor(private productService: ProductService, private router: Router) {}

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    this.productService.getProducts().subscribe((data: any[]) => this.products = data);
  }

  addProduct() {
    this.router.navigate(['products', 'new']);
  }

  editProduct(product: any) {
    this.router.navigate(['products', product.id, 'edit']);
  }

  deleteProduct(product: any) {
    if (confirm('¿Seguro que deseas eliminar este producto?')) {
      this.productService.deleteProduct(product.id).subscribe(() => this.loadProducts());
    }
  }
}
