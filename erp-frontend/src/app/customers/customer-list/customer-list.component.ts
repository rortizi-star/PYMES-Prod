import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { CustomerService } from '../customer.service';

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [MatTableModule, MatCardModule, MatIconModule],
  templateUrl: './customer-list.component.html',
  styleUrl: './customer-list.component.scss'
})
export class CustomerListComponent implements OnInit {
  customers: any[] = [];
  displayedColumns: string[] = ['name', 'email', 'phone', 'isActive'];

  constructor(private customerService: CustomerService) {}

  ngOnInit() {
    this.customerService.getCustomers().subscribe(data => this.customers = data);
  }
}
