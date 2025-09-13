import { Component, OnInit } from '@angular/core';

import { CurrencyPipe, CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { CashRegisterService } from '../cash-register.service';

@Component({
  selector: 'app-cash-register-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatIconModule, MatButtonModule, CurrencyPipe],
  templateUrl: './cash-register-list.component.html',
  styleUrl: './cash-register-list.component.scss'
})
export class CashRegisterListComponent implements OnInit {
  cashRegisters: any[] = [];
  displayedColumns: string[] = ['name', 'balance', 'isActive', 'actions'];

  constructor(private cashRegisterService: CashRegisterService, private router: Router) {}

  ngOnInit() {
    this.loadCashRegisters();
  }

  loadCashRegisters() {
    this.cashRegisterService.getCashRegisters().subscribe(data => this.cashRegisters = data);
  }

  addCashRegister() {
    this.router.navigate(['cash-registers', 'nuevo']);
  }

  editCashRegister(cashRegister: any) {
    this.router.navigate(['cash-registers', 'editar', cashRegister.id]);
  }

  deleteCashRegister(cashRegister: any) {
    if (confirm('¿Seguro que deseas eliminar esta caja chica?')) {
      this.cashRegisterService.deleteCashRegister(cashRegister.id).subscribe(() => this.loadCashRegisters());
    }
  }
}
