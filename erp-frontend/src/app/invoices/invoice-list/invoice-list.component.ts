import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { DatePipe, CurrencyPipe, CommonModule } from '@angular/common';
import { InvoiceService } from '../invoice.service';

@Component({
  selector: 'app-invoice-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatIconModule, MatButtonModule, DatePipe, CurrencyPipe],
  templateUrl: './invoice-list.component.html',
  styleUrl: './invoice-list.component.scss'
})
export class InvoiceListComponent implements OnInit {
  invoices: any[] = [];
  displayedColumns: string[] = ['number', 'date', 'customer', 'total', 'isActive', 'actions'];

  constructor(private invoiceService: InvoiceService, private router: Router) {}

  ngOnInit() {
    this.loadInvoices();
  }

  loadInvoices() {
    this.invoiceService.getInvoices().subscribe(data => this.invoices = data);
  }

  addInvoice() {
    this.router.navigate(['invoices', 'nuevo']);
  }

  editInvoice(invoice: any) {
    this.router.navigate(['invoices', 'editar', invoice.id]);
  }

  deleteInvoice(invoice: any) {
    if (confirm('¿Seguro que deseas eliminar esta factura?')) {
      this.invoiceService.deleteInvoice(invoice.id).subscribe(() => this.loadInvoices());
    }
  }
}
