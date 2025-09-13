import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { JournalEntryService } from '../journal-entry.service';

@Component({
  selector: 'app-journal-entry-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule,
    MatProgressBarModule
  ],
  templateUrl: './journal-entry-form.component.html',
  styleUrl: './journal-entry-form.component.scss'
})
export class JournalEntryFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  journalEntryId: number | null = null;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private journalEntryService: JournalEntryService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      date: ['', [Validators.required]],
      description: ['', [Validators.required]],
      amount: [0, [Validators.required, Validators.min(0)]],
      isActive: [true]
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.journalEntryId = +id;
      this.loading = true;
      this.journalEntryService.getJournalEntry(this.journalEntryId).subscribe({
        next: entry => {
          this.form.patchValue({
            date: entry.date,
            description: entry.description,
            amount: entry.amount,
            isActive: entry.isActive
          });
          this.loading = false;
        },
        error: err => {
          this.error = 'No se pudo cargar el asiento';
          this.loading = false;
        }
      });
    } else {
      this.isEdit = false;
    }
  }

  save() {
    if (this.form.invalid) return;
    this.loading = true;
    const entryData = { ...this.form.value };
    if (this.isEdit && this.journalEntryId) {
      this.journalEntryService.updateJournalEntry(this.journalEntryId, entryData).subscribe({
        next: () => this.router.navigate(['/journal-entries']),
        error: err => {
          this.error = 'Error al actualizar asiento';
          this.loading = false;
        }
      });
    } else {
      this.journalEntryService.createJournalEntry(entryData).subscribe({
        next: () => this.router.navigate(['/journal-entries']),
        error: err => {
          this.error = 'Error al crear asiento';
          this.loading = false;
        }
      });
    }
  }

  cancel() {
    this.router.navigate(['/journal-entries']);
  }
}
