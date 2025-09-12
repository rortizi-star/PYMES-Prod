import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-account-list',
  standalone: true,
  imports: [MatTableModule, MatCardModule, MatIconModule],
  templateUrl: './account-list.component.html',
  styleUrl: './account-list.component.scss'
})
export class AccountListComponent implements OnInit {
  accounts: any[] = [];
  displayedColumns: string[] = ['code', 'name', 'type', 'level', 'isActive'];

  constructor(private accountService: AccountService) {}

  ngOnInit() {
    this.accountService.getAccounts().subscribe(data => this.accounts = data);
  }
}
