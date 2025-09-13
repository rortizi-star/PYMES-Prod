
import { Component, DoCheck } from '@angular/core';
import { FinancialReportsApiService } from './financial-reports-api.service';
import * as XLSX from 'xlsx';
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import { ChartConfiguration, ChartType } from 'chart.js';

@Component({
  selector: 'app-financial-reports',
  templateUrl: './financial-reports.component.html',
  styleUrls: ['./financial-reports.component.scss']
})
export class FinancialReportsComponent implements DoCheck {
  year = new Date().getFullYear();
  month = new Date().getMonth() + 1;
  loading = false;
  selectedReport: string | null = null;
  reportData: any = null;
  chartData: ChartConfiguration['data'] | null = null;
  chartType: ChartType = 'bar';

  constructor(private api: FinancialReportsApiService) {}

  ngDoCheck() {
    this.updateChart();
  }

  selectReport(report: string) {
    this.selectedReport = report;
    this.reportData = null;
    this.loading = true;
    let obs$;
    switch (report) {
      case 'balance':
        obs$ = this.api.getBalanceGeneral(this.year, this.month); break;
      case 'resultados':
        obs$ = this.api.getEstadoResultados(this.year, this.month); break;
      case 'mayor':
        obs$ = this.api.getLibroMayor(this.year, this.month); break;
      case 'diario':
        obs$ = this.api.getLibroDiario(this.year, this.month); break;
      case 'auxiliares':
        obs$ = this.api.getAuxiliares(this.year, this.month); break;
      default:
        obs$ = null;
    }
    if (obs$) {
      obs$.subscribe({
        next: data => { this.reportData = data; this.loading = false; },
        error: () => { this.reportData = { error: 'Error al obtener el reporte' }; this.loading = false; }
      });
    } else {
      this.loading = false;
    }
  }

  getColumns(data: any[]): string[] {
    if (!data || !data.length) return [];
    return Object.keys(data[0]);
  }

  updateChart() {
    if (this.reportData && this.reportData.data && this.reportData.data.length) {
      const cols = this.getColumns(this.reportData.data);
      if (cols.length >= 2) {
        this.chartData = {
          labels: this.reportData.data.map((row: any) => row[cols[0]]),
          datasets: [
            {
              label: cols[1],
              data: this.reportData.data.map((row: any) => row[cols[1]]),
              backgroundColor: '#1976d2'
            }
          ]
        };
      } else {
        this.chartData = null;
      }
    } else {
      this.chartData = null;
    }
  }

  exportToExcel(): void {
    if (!this.reportData || !this.reportData.data || !this.reportData.data.length) return;
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(this.reportData.data);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Reporte');
    XLSX.writeFile(wb, `reporte-financiero-${this.selectedReport || 'general'}.xlsx`);
  }

  exportToPDF(): void {
    if (!this.reportData || !this.reportData.data || !this.reportData.data.length) return;
    const doc = new jsPDF();
    // @ts-ignore
    doc.autoTable({
      head: [this.getColumns(this.reportData.data)],
      body: this.reportData.data.map((row: any) => this.getColumns(this.reportData.data).map((col: string) => row[col]))
    });
    doc.save(`reporte-financiero-${this.selectedReport || 'general'}.pdf`);
  }
}
