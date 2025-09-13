import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { CustomerService } from '../customer.service';

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [MatTableModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './customer-list.component.html',
  styleUrl: './customer-list.component.scss'
})
export class CustomerListComponent implements OnInit {
  customers: any[] = [];
  displayedColumns: string[] = ['name', 'email', 'phone', 'isActive', 'actions'];

  constructor(private customerService: CustomerService, private router: Router) {}

  ngOnInit() {
    this.loadCustomers();
  }

  loadCustomers() {
    this.customerService.getCustomers().subscribe(data => this.customers = data);
  }

  addCustomer() {
    this.router.navigate(['customers', 'new']);
  }

  editCustomer(customer: any) {
    this.router.navigate(['customers', customer.id, 'edit']);
  }

  deleteCustomer(customer: any) {
    if (confirm('¿Seguro que deseas eliminar este cliente?')) {
      this.customerService.deleteCustomer(customer.id).subscribe(() => this.loadCustomers());
    }
  }
}
