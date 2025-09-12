package gob.gt.com.lab.demo.controller;

import gob.gt.com.lab.demo.entity.*;
import gob.gt.com.lab.demo.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/banks")
public class BankController {
    @Autowired
    private BankService bankService;

    @GetMapping
    public List<Bank> getAllBanks() { return bankService.getAllBanks(); }

    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable Long id) {
        return bankService.getBankById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Bank createBank(@RequestBody Bank bank) { return bankService.createBank(bank); }

    @PutMapping("/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody Bank bank) {
        Bank updated = bankService.updateBank(id, bank);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
        if (bankService.deleteBank(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    // Cuentas por banco
    @GetMapping("/{id}/accounts")
    public List<BankAccount> getAccountsByBank(@PathVariable Long id) {
        return bankService.getAccountsByBank(id);
    }

    // Transacciones por cuenta
    @GetMapping("/accounts/{accountId}/transactions")
    public List<BankTransaction> getTransactionsByAccount(@PathVariable Long accountId) {
        return bankService.getTransactionsByAccount(accountId);
    }

    // Saldo de cuenta
    @GetMapping("/accounts/{accountId}/balance")
    public Double getBalanceByAccount(@PathVariable Long accountId) {
        return bankService.getBalanceByAccount(accountId);
    }

    // Crear transacción
    @PostMapping("/accounts/{accountId}/transactions")
    public BankTransaction createTransaction(@PathVariable Long accountId, @RequestBody BankTransaction tx) {
        BankAccount acc = new BankAccount();
        acc.setId(accountId);
        tx.setBankAccount(acc);
        return bankService.createTransaction(tx);
    }
}
