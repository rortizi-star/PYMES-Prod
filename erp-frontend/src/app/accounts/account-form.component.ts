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
import { AccountService } from './account.service';

@Component({
  selector: 'app-account-form',
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
  templateUrl: './account-form.component.html',
  styleUrl: './account-form.component.scss'
})
export class AccountFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  accountId: number | null = null;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      code: ['', [Validators.required]],
      name: ['', [Validators.required]],
      type: ['', [Validators.required]],
      level: [1, [Validators.required, Validators.min(1)]],
      isActive: [true]
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.accountId = +id;
      this.loading = true;
      this.accountService.getAccount(this.accountId).subscribe({
        next: acc => {
          this.form.patchValue({
            code: acc.code,
            name: acc.name,
            type: acc.type,
            level: acc.level,
            isActive: acc.isActive
          });
          this.loading = false;
        },
        error: err => {
          this.error = 'No se pudo cargar la cuenta';
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
    const accountData = { ...this.form.value };
    if (this.isEdit && this.accountId) {
      this.accountService.updateAccount(this.accountId, accountData).subscribe({
        next: () => this.router.navigate(['/accounts']),
        error: err => {
          this.error = 'Error al actualizar cuenta';
          this.loading = false;
        }
      });
    } else {
      this.accountService.createAccount(accountData).subscribe({
        next: () => this.router.navigate(['/accounts']),
        error: err => {
          this.error = 'Error al crear cuenta';
          this.loading = false;
        }
      });
    }
  }

  cancel() {
    this.router.navigate(['/accounts']);
  }
}
