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
import { InvoiceService } from '../invoice.service';

@Component({
  selector: 'app-invoice-form',
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
  templateUrl: './invoice-form.component.html',
  styleUrl: './invoice-form.component.scss'
})
export class InvoiceFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  invoiceId: number | null = null;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private invoiceService: InvoiceService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      number: ['', [Validators.required]],
      date: ['', [Validators.required]],
      customer: ['', [Validators.required]],
      total: [0, [Validators.required, Validators.min(0)]],
      isActive: [true]
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.invoiceId = +id;
      this.loading = true;
      this.invoiceService.getInvoice(this.invoiceId).subscribe({
        next: invoice => {
          this.form.patchValue({
            number: invoice.number,
            date: invoice.date,
            customer: invoice.customer,
            total: invoice.total,
            isActive: invoice.isActive
          });
          this.loading = false;
        },
        error: err => {
          this.error = 'No se pudo cargar la factura';
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
    const invoiceData = { ...this.form.value };
    if (this.isEdit && this.invoiceId) {
      this.invoiceService.updateInvoice(this.invoiceId, invoiceData).subscribe({
        next: () => this.router.navigate(['/invoices']),
        error: err => {
          this.error = 'Error al actualizar factura';
          this.loading = false;
        }
      });
    } else {
      this.invoiceService.createInvoice(invoiceData).subscribe({
        next: () => this.router.navigate(['/invoices']),
        error: err => {
          this.error = 'Error al crear factura';
          this.loading = false;
        }
      });
    }
  }

  cancel() {
    this.router.navigate(['/invoices']);
  }
}
