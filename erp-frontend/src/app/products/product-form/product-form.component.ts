import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule
  ],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.scss'
})
export class ProductFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  productId: number | null = null;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      code: ['', [Validators.required]],
      price: [0, [Validators.required, Validators.min(0)]],
      isActive: [true]
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.productId = +id;
      this.loading = true;
      this.productService.getProduct(this.productId).subscribe({
        next: prod => {
          this.form.patchValue({
            name: prod.name,
            code: prod.code,
            price: prod.price,
            isActive: prod.isActive
          });
          this.loading = false;
        },
        error: err => {
          this.error = 'No se pudo cargar el producto';
          this.loading = false;
        }
      });
    } else {
      this.isEdit = false;
    }
  }

  save() {
    if (this.form.invalid) return;
    this.loading = true;
    const productData = { ...this.form.value };
    if (this.isEdit && this.productId) {
      this.productService.updateProduct(this.productId, productData).subscribe({
        next: () => this.router.navigate(['/products']),
        error: err => {
          this.error = 'Error al actualizar producto';
          this.loading = false;
        }
      });
    } else {
      this.productService.createProduct(productData).subscribe({
        next: () => this.router.navigate(['/products']),
        error: err => {
          this.error = 'Error al crear producto';
          this.loading = false;
        }
      });
    }
  }

  cancel() {
    this.router.navigate(['/products']);
  }
}
