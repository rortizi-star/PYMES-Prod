package gob.gt.com.lab.demo.controller;

import gob.gt.com.lab.demo.entity.*;
import gob.gt.com.lab.demo.service.CashRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cash-registers")
public class CashRegisterController {
    @Autowired
    private CashRegisterService cashRegisterService;

    @GetMapping
    public List<CashRegister> getAllCashRegisters() { return cashRegisterService.getAllCashRegisters(); }

    @GetMapping("/{id}")
    public ResponseEntity<CashRegister> getCashRegisterById(@PathVariable Long id) {
        return cashRegisterService.getCashRegisterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CashRegister createCashRegister(@RequestBody CashRegister cr) { return cashRegisterService.createCashRegister(cr); }

    @PutMapping("/{id}")
    public ResponseEntity<CashRegister> updateCashRegister(@PathVariable Long id, @RequestBody CashRegister cr) {
        CashRegister updated = cashRegisterService.updateCashRegister(id, cr);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCashRegister(@PathVariable Long id) {
        if (cashRegisterService.deleteCashRegister(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    // Transacciones por caja
    @GetMapping("/{id}/transactions")
    public List<CashTransaction> getTransactionsByRegister(@PathVariable Long id) {
        return cashRegisterService.getTransactionsByRegister(id);
    }

    // Saldo de caja
    @GetMapping("/{id}/balance")
    public Double getBalanceByRegister(@PathVariable Long id) {
        return cashRegisterService.getBalanceByRegister(id);
    }

    // Crear transacción
    @PostMapping("/{id}/transactions")
    public CashTransaction createTransaction(@PathVariable Long id, @RequestBody CashTransaction tx) {
        CashRegister cr = new CashRegister();
        cr.setId(id);
        tx.setCashRegister(cr);
        return cashRegisterService.createTransaction(tx);
    }
}
