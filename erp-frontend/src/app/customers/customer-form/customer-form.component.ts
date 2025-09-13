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
import { CustomerService } from '../customer.service';

@Component({
  selector: 'app-customer-form',
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
  templateUrl: './customer-form.component.html',
  styleUrl: './customer-form.component.scss'
})
export class CustomerFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  customerId: number | null = null;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required]],
      isActive: [true]
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.customerId = +id;
      this.loading = true;
      this.customerService.getCustomer(this.customerId).subscribe({
        next: cust => {
          this.form.patchValue({
            name: cust.name,
            email: cust.email,
            phone: cust.phone,
            isActive: cust.isActive
          });
          this.loading = false;
        },
        error: err => {
          this.error = 'No se pudo cargar el cliente';
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
    const customerData = { ...this.form.value };
    if (this.isEdit && this.customerId) {
      this.customerService.updateCustomer(this.customerId, customerData).subscribe({
        next: () => this.router.navigate(['/customers']),
        error: err => {
          this.error = 'Error al actualizar cliente';
          this.loading = false;
        }
      });
    } else {
      this.customerService.createCustomer(customerData).subscribe({
        next: () => this.router.navigate(['/customers']),
        error: err => {
          this.error = 'Error al crear cliente';
          this.loading = false;
        }
      });
    }
  }

  cancel() {
    this.router.navigate(['/customers']);
  }
}
