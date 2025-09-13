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
import { SupplierService } from '../supplier.service';

@Component({
  selector: 'app-supplier-form',
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
  templateUrl: './supplier-form.component.html',
  styleUrl: './supplier-form.component.scss'
})
export class SupplierFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  supplierId: number | null = null;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private supplierService: SupplierService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      contact: ['', [Validators.required]],
      phone: ['', [Validators.required]],
      isActive: [true]
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.supplierId = +id;
      this.loading = true;
      this.supplierService.getSupplier(this.supplierId).subscribe({
        next: supplier => {
          this.form.patchValue({
            name: supplier.name,
            contact: supplier.contact,
            phone: supplier.phone,
            isActive: supplier.isActive
          });
          this.loading = false;
        },
        error: err => {
          this.error = 'No se pudo cargar el proveedor';
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
    const supplierData = { ...this.form.value };
    if (this.isEdit && this.supplierId) {
      this.supplierService.updateSupplier(this.supplierId, supplierData).subscribe({
        next: () => this.router.navigate(['/suppliers']),
        error: err => {
          this.error = 'Error al actualizar proveedor';
          this.loading = false;
        }
      });
    } else {
      this.supplierService.createSupplier(supplierData).subscribe({
        next: () => this.router.navigate(['/suppliers']),
        error: err => {
          this.error = 'Error al crear proveedor';
          this.loading = false;
        }
      });
    }
  }

  cancel() {
    this.router.navigate(['/suppliers']);
  }
}
