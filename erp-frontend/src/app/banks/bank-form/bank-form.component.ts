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
import { BankService } from '../bank.service';

@Component({
  selector: 'app-bank-form',
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
  templateUrl: './bank-form.component.html',
  styleUrl: './bank-form.component.scss'
})
export class BankFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  bankId: number | null = null;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private bankService: BankService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      code: ['', [Validators.required]],
      isActive: [true]
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.bankId = +id;
      this.loading = true;
      this.bankService.getBank(this.bankId).subscribe({
        next: bank => {
          this.form.patchValue({
            name: bank.name,
            code: bank.code,
            isActive: bank.isActive
          });
          this.loading = false;
        },
        error: err => {
          this.error = 'No se pudo cargar el banco';
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
    const bankData = { ...this.form.value };
    if (this.isEdit && this.bankId) {
      this.bankService.updateBank(this.bankId, bankData).subscribe({
        next: () => this.router.navigate(['/banks']),
        error: err => {
          this.error = 'Error al actualizar banco';
          this.loading = false;
        }
      });
    } else {
      this.bankService.createBank(bankData).subscribe({
        next: () => this.router.navigate(['/banks']),
        error: err => {
          this.error = 'Error al crear banco';
          this.loading = false;
        }
      });
    }
  }

  cancel() {
    this.router.navigate(['/banks']);
  }
}
