import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { DatePipe, CommonModule } from '@angular/common';
import { JournalEntryService } from '../journal-entry.service';

@Component({
  selector: 'app-journal-entry-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatIconModule, MatButtonModule, DatePipe],
  templateUrl: './journal-entry-list.component.html',
  styleUrl: './journal-entry-list.component.scss'
})
export class JournalEntryListComponent implements OnInit {
  journalEntries: any[] = [];
  displayedColumns: string[] = ['date', 'description', 'amount', 'isActive', 'actions'];

  constructor(private journalEntryService: JournalEntryService, private router: Router) {}

  ngOnInit() {
    this.loadJournalEntries();
  }

  loadJournalEntries() {
    this.journalEntryService.getJournalEntries().subscribe(data => this.journalEntries = data);
  }

  addJournalEntry() {
    this.router.navigate(['journal-entries', 'nuevo']);
  }

  editJournalEntry(entry: any) {
    this.router.navigate(['journal-entries', 'editar', entry.id]);
  }

  deleteJournalEntry(entry: any) {
    if (confirm('¿Seguro que deseas eliminar este asiento?')) {
      this.journalEntryService.deleteJournalEntry(entry.id).subscribe(() => this.loadJournalEntries());
    }
  }
}
