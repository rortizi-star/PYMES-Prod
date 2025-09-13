import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { RoleService } from '../role.service';

@Component({
  selector: 'app-role-list',
  standalone: true,
  imports: [MatTableModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './role-list.component.html',
  styleUrl: './role-list.component.scss'
})
export class RoleListComponent implements OnInit {
  roles: any[] = [];
  displayedColumns: string[] = ['name', 'description', 'isActive', 'actions'];

  constructor(private roleService: RoleService, private router: Router) {}

  ngOnInit() {
    this.loadRoles();
  }

  loadRoles() {
    this.roleService.getRoles().subscribe(data => this.roles = data);
  }

  addRole() {
    this.router.navigate(['roles', 'nuevo']);
  }

  editRole(role: any) {
    this.router.navigate(['roles', 'editar', role.id]);
  }

  deleteRole(role: any) {
    if (confirm('¿Seguro que deseas eliminar este rol?')) {
      this.roleService.deleteRole(role.id).subscribe(() => this.loadRoles());
    }
  }
}
