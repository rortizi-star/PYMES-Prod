import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class FinancialReportsApiService {
  private baseUrl = 'http://localhost:8082/api/financial-reports';

  constructor(private http: HttpClient) {}

  getBalanceGeneral(year: number, month: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/balance-general`, { params: { year, month } });
  }

  getEstadoResultados(year: number, month: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/estado-resultados`, { params: { year, month } });
  }

  getLibroMayor(year: number, month: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/libro-mayor`, { params: { year, month } });
  }

  getLibroDiario(year: number, month: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/libro-diario`, { params: { year, month } });
  }

  getAuxiliares(year: number, month: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/auxiliares`, { params: { year, month } });
  }
}
