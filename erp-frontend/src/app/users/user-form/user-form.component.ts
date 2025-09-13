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
import { UserService } from '../user.service';

@Component({
  selector: 'app-user-form',
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
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.scss'
})
export class UserFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  userId: number | null = null;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      username: ['', [Validators.required]],
      fullName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', []],
      isActive: [true]
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.userId = +id;
      this.loading = true;
      this.userService.getUser(this.userId).subscribe({
        next: user => {
          this.form.patchValue({
            username: user.username,
            fullName: user.fullName,
            email: user.email,
            isActive: user.isActive
          });
          this.form.get('password')?.clearValidators();
          this.form.get('password')?.updateValueAndValidity();
          this.loading = false;
        },
        error: err => {
          this.error = 'No se pudo cargar el usuario';
          this.loading = false;
        }
      });
    } else {
      this.isEdit = false;
      this.form.get('password')?.setValidators([Validators.required]);
      this.form.get('password')?.updateValueAndValidity();
    }
  }

  save() {
    if (this.form.invalid) return;
    this.loading = true;
    const userData = { ...this.form.value };
    if (this.isEdit && this.userId) {
      if (!userData.password) delete userData.password;
      this.userService.updateUser(this.userId, userData).subscribe({
        next: () => this.router.navigate(['/users']),
        error: err => {
          this.error = 'Error al actualizar usuario';
          this.loading = false;
        }
      });
    } else {
      this.userService.createUser(userData).subscribe({
        next: () => this.router.navigate(['/users']),
        error: err => {
          this.error = 'Error al crear usuario';
          this.loading = false;
        }
      });
    }
  }

  cancel() {
    this.router.navigate(['/users']);
  }
}
