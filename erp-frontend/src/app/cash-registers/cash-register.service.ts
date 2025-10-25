
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CashRegisterService {
  private apiUrl = 'http://localhost:8082/api/cash-registers';

  constructor(private http: HttpClient) { }

  getCashRegisters(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getCashRegister(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  createCashRegister(cashRegister: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, cashRegister);
  }

  updateCashRegister(id: number, cashRegister: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, cashRegister);
  }

  deleteCashRegister(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
