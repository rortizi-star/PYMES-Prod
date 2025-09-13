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
import { RoleService } from '../role.service';

@Component({
  selector: 'app-role-form',
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
  templateUrl: './role-form.component.html',
  styleUrl: './role-form.component.scss'
})
export class RoleFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  roleId: number | null = null;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private roleService: RoleService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      isActive: [true]
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.roleId = +id;
      this.loading = true;
      this.roleService.getRole(this.roleId).subscribe({
        next: role => {
          this.form.patchValue({
            name: role.name,
            description: role.description,
            isActive: role.isActive
          });
          this.loading = false;
        },
        error: err => {
          this.error = 'No se pudo cargar el rol';
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
    const roleData = { ...this.form.value };
    if (this.isEdit && this.roleId) {
      this.roleService.updateRole(this.roleId, roleData).subscribe({
        next: () => this.router.navigate(['/roles']),
        error: err => {
          this.error = 'Error al actualizar rol';
          this.loading = false;
        }
      });
    } else {
      this.roleService.createRole(roleData).subscribe({
        next: () => this.router.navigate(['/roles']),
        error: err => {
          this.error = 'Error al crear rol';
          this.loading = false;
        }
      });
    }
  }

  cancel() {
    this.router.navigate(['/roles']);
  }
}
