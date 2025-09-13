import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [MatTableModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent implements OnInit {
  users: any[] = [];
  displayedColumns: string[] = ['username', 'fullName', 'email', 'isActive', 'actions'];

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getUsers().subscribe(data => this.users = data);
  }

  addUser() {
    this.router.navigate(['users', 'new']);
  }

  editUser(user: any) {
    this.router.navigate(['users', user.id, 'edit']);
  }

  deleteUser(user: any) {
    if (confirm('¿Seguro que deseas eliminar este usuario?')) {
      this.userService.deleteUser(user.id).subscribe(() => this.loadUsers());
    }
  }
}
