import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../accounts/account.service';
import { UserService } from '../users/user.service';
import { CustomerService } from '../customers/customer.service';

import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [MatCardModule, MatIconModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  userName = 'Usuario';
  totalAccounts = 0;
  totalUsers = 0;
  totalCustomers = 0;

  constructor(
    private router: Router,
    private accountService: AccountService,
    private userService: UserService,
    private customerService: CustomerService
  ) {}

  ngOnInit() {
    this.accountService.getAccounts().subscribe(data => this.totalAccounts = data.length);
    this.userService.getUsers().subscribe(data => this.totalUsers = data.length);
    this.customerService.getCustomers().subscribe(data => this.totalCustomers = data.length);
  }

  goTo(module: string) {
    this.router.navigate(['/' + module]);
  }
}
