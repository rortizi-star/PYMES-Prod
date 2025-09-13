import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { BankService } from '../bank.service';

@Component({
  selector: 'app-bank-list',
  standalone: true,
  imports: [MatTableModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './bank-list.component.html',
  styleUrl: './bank-list.component.scss'
})
export class BankListComponent implements OnInit {
  banks: any[] = [];
  displayedColumns: string[] = ['name', 'code', 'isActive', 'actions'];

  constructor(private bankService: BankService, private router: Router) {}

  ngOnInit() {
    this.loadBanks();
  }

  loadBanks() {
    this.bankService.getBanks().subscribe(data => this.banks = data);
  }

  addBank() {
    this.router.navigate(['banks', 'new']);
  }

  editBank(bank: any) {
    this.router.navigate(['banks', bank.id, 'edit']);
  }

  deleteBank(bank: any) {
    if (confirm('¿Seguro que deseas eliminar este banco?')) {
      this.bankService.deleteBank(bank.id).subscribe(() => this.loadBanks());
    }
  }
}
