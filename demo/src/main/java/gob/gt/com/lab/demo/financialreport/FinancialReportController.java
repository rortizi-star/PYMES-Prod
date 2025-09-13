package gob.gt.com.lab.demo.financialreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/financial-reports")
public class FinancialReportController {
    @Autowired
    private FinancialReportService service;

    @GetMapping("/balance-general")
    public Map<String, Object> getBalanceGeneral(@RequestParam int year, @RequestParam int month) {
        return service.getBalanceGeneral(year, month);
    }

    @GetMapping("/estado-resultados")
    public Map<String, Object> getEstadoResultados(@RequestParam int year, @RequestParam int month) {
        return service.getEstadoResultados(year, month);
    }

    @GetMapping("/libro-mayor")
    public Map<String, Object> getLibroMayor(@RequestParam int year, @RequestParam int month) {
        return service.getLibroMayor(year, month);
    }

    @GetMapping("/libro-diario")
    public Map<String, Object> getLibroDiario(@RequestParam int year, @RequestParam int month) {
        return service.getLibroDiario(year, month);
    }

    @GetMapping("/auxiliares")
    public Map<String, Object> getAuxiliares(@RequestParam int year, @RequestParam int month) {
        return service.getAuxiliares(year, month);
    }
}
