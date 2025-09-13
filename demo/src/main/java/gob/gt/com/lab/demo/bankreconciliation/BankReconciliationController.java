package gob.gt.com.lab.demo.bankreconciliation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
// Forzar recarga de métodos recientes

@RestController
@RequestMapping("/api/bank-reconciliation")
public class BankReconciliationController {
    @Autowired
    private BankReconciliationService service;

    @GetMapping
    public List<BankReconciliation> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<BankReconciliation> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public BankReconciliation create(@RequestBody BankReconciliation reconciliation) {
        return service.save(reconciliation);
    }

    @PutMapping("/{id}")
    public BankReconciliation update(@PathVariable Long id, @RequestBody BankReconciliation reconciliation) {
        reconciliation.setId(id);
        return service.save(reconciliation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}/movements")
    public List<BankReconciliationMovement> getMovements(@PathVariable Long id) {
        return service.findMovementsByReconciliation(id);
    }

    @PostMapping("/{id}/movements")
    public BankReconciliationMovement addMovement(@PathVariable Long id, @RequestBody BankReconciliationMovement movement) {
        BankReconciliation reconciliation = service.findById(id).orElseThrow();
        movement.setBankReconciliation(reconciliation);
        return service.saveMovement(movement);
    }
}
