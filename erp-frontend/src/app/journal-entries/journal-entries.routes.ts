import { Routes } from '@angular/router';
import { JournalEntryListComponent } from './journal-entry-list/journal-entry-list.component';
import { JournalEntryFormComponent } from './journal-entry-form/journal-entry-form.component';

export const journalEntriesRoutes: Routes = [
  { path: '', component: JournalEntryListComponent },
  { path: 'nuevo', component: JournalEntryFormComponent },
  { path: 'editar/:id', component: JournalEntryFormComponent }
];
