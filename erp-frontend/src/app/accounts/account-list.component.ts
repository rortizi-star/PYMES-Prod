import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { AccountService } from './account.service';

@Component({
  selector: 'app-account-list',
  standalone: true,
  imports: [MatTableModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './account-list.component.html',
  styleUrl: './account-list.component.scss'
})
export class AccountListComponent implements OnInit {
  accounts: any[] = [];
  displayedColumns: string[] = ['code', 'name', 'type', 'level', 'isActive', 'actions'];

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit() {
    this.loadAccounts();
  }

  loadAccounts() {
    this.accountService.getAccounts().subscribe(data => this.accounts = data);
  }

  addAccount() {
    this.router.navigate(['accounts', 'new']);
  }

  editAccount(account: any) {
    this.router.navigate(['accounts', account.id, 'edit']);
  }

  deleteAccount(account: any) {
    if (confirm('¿Seguro que deseas eliminar esta cuenta?')) {
      this.accountService.deleteAccount(account.id).subscribe(() => this.loadAccounts());
    }
  }
}
