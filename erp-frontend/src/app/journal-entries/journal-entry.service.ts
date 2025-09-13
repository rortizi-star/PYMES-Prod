import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JournalEntryService {
  private apiUrl = 'http://localhost:8080/api/journal-entries';

  constructor(private http: HttpClient) { }

  getJournalEntries(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getJournalEntry(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  createJournalEntry(journalEntry: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, journalEntry);
  }

  updateJournalEntry(id: number, journalEntry: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, journalEntry);
  }

  deleteJournalEntry(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
