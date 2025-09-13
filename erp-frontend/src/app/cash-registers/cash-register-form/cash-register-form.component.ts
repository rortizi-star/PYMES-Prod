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
import { CashRegisterService } from '../cash-register.service';

@Component({
  selector: 'app-cash-register-form',
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
  templateUrl: './cash-register-form.component.html',
  styleUrl: './cash-register-form.component.scss'
})
export class CashRegisterFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  cashRegisterId: number | null = null;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private cashRegisterService: CashRegisterService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      balance: [0, [Validators.required, Validators.min(0)]],
      isActive: [true]
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.cashRegisterId = +id;
      this.loading = true;
      this.cashRegisterService.getCashRegister(this.cashRegisterId).subscribe({
        next: cashRegister => {
          this.form.patchValue({
            name: cashRegister.name,
            balance: cashRegister.balance,
            isActive: cashRegister.isActive
          });
          this.loading = false;
        },
        error: err => {
          this.error = 'No se pudo cargar la caja chica';
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
    const cashRegisterData = { ...this.form.value };
    if (this.isEdit && this.cashRegisterId) {
      this.cashRegisterService.updateCashRegister(this.cashRegisterId, cashRegisterData).subscribe({
        next: () => this.router.navigate(['/cash-registers']),
        error: err => {
          this.error = 'Error al actualizar caja chica';
          this.loading = false;
        }
      });
    } else {
      this.cashRegisterService.createCashRegister(cashRegisterData).subscribe({
        next: () => this.router.navigate(['/cash-registers']),
        error: err => {
          this.error = 'Error al crear caja chica';
          this.loading = false;
        }
      });
    }
  }

  cancel() {
    this.router.navigate(['/cash-registers']);
  }
}
