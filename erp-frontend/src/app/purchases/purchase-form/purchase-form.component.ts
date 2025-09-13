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
import { PurchaseService } from '../purchase.service';

@Component({
  selector: 'app-purchase-form',
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
  templateUrl: './purchase-form.component.html',
  styleUrl: './purchase-form.component.scss'
})
export class PurchaseFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  purchaseId: number | null = null;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private purchaseService: PurchaseService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      number: ['', [Validators.required]],
      date: ['', [Validators.required]],
      supplier: ['', [Validators.required]],
      total: [0, [Validators.required, Validators.min(0)]],
      isActive: [true]
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.purchaseId = +id;
      this.loading = true;
      this.purchaseService.getPurchase(this.purchaseId).subscribe({
        next: purchase => {
          this.form.patchValue({
            number: purchase.number,
            date: purchase.date,
            supplier: purchase.supplier,
            total: purchase.total,
            isActive: purchase.isActive
          });
          this.loading = false;
        },
        error: err => {
          this.error = 'No se pudo cargar la compra';
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
    const purchaseData = { ...this.form.value };
    if (this.isEdit && this.purchaseId) {
      this.purchaseService.updatePurchase(this.purchaseId, purchaseData).subscribe({
        next: () => this.router.navigate(['/purchases']),
        error: err => {
          this.error = 'Error al actualizar compra';
          this.loading = false;
        }
      });
    } else {
      this.purchaseService.createPurchase(purchaseData).subscribe({
        next: () => this.router.navigate(['/purchases']),
        error: err => {
          this.error = 'Error al crear compra';
          this.loading = false;
        }
      });
    }
  }

  cancel() {
    this.router.navigate(['/purchases']);
  }
}
