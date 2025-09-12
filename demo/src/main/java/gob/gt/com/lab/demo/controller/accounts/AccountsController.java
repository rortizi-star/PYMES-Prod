package gob.gt.com.lab.demo.controller.accounts;

import gob.gt.com.lab.demo.entity.accounts.*;
import gob.gt.com.lab.demo.service.accounts.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {
    @Autowired
    private AccountsService accountsService;

    // --- Cuentas por pagar ---
    @GetMapping("/payables")
    public List<AccountPayable> getAllPayables() {
        return accountsService.getAllPayables();
    }
    @GetMapping("/payables/{id}")
    public ResponseEntity<?> getPayable(@PathVariable Long id) {
        return accountsService.getPayable(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/payables/supplier/{supplierId}")
    public List<AccountPayable> getPayablesBySupplier(@PathVariable Long supplierId) {
        return accountsService.getPayablesBySupplier(supplierId);
    }
    @GetMapping("/payables/due-date")
    public List<AccountPayable> getPayablesByDueDate(@RequestParam String start, @RequestParam String end) {
        return accountsService.getPayablesByDueDate(LocalDate.parse(start), LocalDate.parse(end));
    }
    @GetMapping("/payables/pending")
    public List<AccountPayable> getPayablesPending() {
        return accountsService.getPayablesBySettled(false);
    }
    @PostMapping("/payables")
    public ResponseEntity<?> createPayable(@RequestBody Map<String, Object> req) {
        try {
            Long supplierId = Long.valueOf(req.get("supplierId").toString());
            Double amount = Double.valueOf(req.get("amount").toString());
            LocalDate dueDate = LocalDate.parse(req.get("dueDate").toString());
            String description = req.get("description") != null ? req.get("description").toString() : null;
            AccountPayable ap = accountsService.createPayable(supplierId, amount, dueDate, description);
            return ResponseEntity.ok(ap);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/payables/payment")
    public ResponseEntity<?> registerPayment(@RequestBody Map<String, Object> req) {
        try {
            Long payableId = Long.valueOf(req.get("payableId").toString());
            Double amount = Double.valueOf(req.get("amount").toString());
            String method = req.get("method") != null ? req.get("method").toString() : null;
            String reference = req.get("reference") != null ? req.get("reference").toString() : null;
            Payment payment = accountsService.registerPayment(payableId, amount, method, reference);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/payables/{id}/payments")
    public List<Payment> getPaymentsByPayable(@PathVariable Long id) {
        return accountsService.getPaymentsByPayable(id);
    }
    @GetMapping("/payables/payments/date-range")
    public List<Payment> getPaymentsByDate(@RequestParam String start, @RequestParam String end) {
        return accountsService.getPaymentsByDate(LocalDate.parse(start), LocalDate.parse(end));
    }

    // --- Cuentas por cobrar ---
    @GetMapping("/receivables")
    public List<AccountReceivable> getAllReceivables() {
        return accountsService.getAllReceivables();
    }
    @GetMapping("/receivables/{id}")
    public ResponseEntity<?> getReceivable(@PathVariable Long id) {
        return accountsService.getReceivable(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/receivables/customer/{customerId}")
    public List<AccountReceivable> getReceivablesByCustomer(@PathVariable Long customerId) {
        return accountsService.getReceivablesByCustomer(customerId);
    }
    @GetMapping("/receivables/due-date")
    public List<AccountReceivable> getReceivablesByDueDate(@RequestParam String start, @RequestParam String end) {
        return accountsService.getReceivablesByDueDate(LocalDate.parse(start), LocalDate.parse(end));
    }
    @GetMapping("/receivables/pending")
    public List<AccountReceivable> getReceivablesPending() {
        return accountsService.getReceivablesBySettled(false);
    }
    @PostMapping("/receivables")
    public ResponseEntity<?> createReceivable(@RequestBody Map<String, Object> req) {
        try {
            Long customerId = Long.valueOf(req.get("customerId").toString());
            Double amount = Double.valueOf(req.get("amount").toString());
            LocalDate dueDate = LocalDate.parse(req.get("dueDate").toString());
            String description = req.get("description") != null ? req.get("description").toString() : null;
            AccountReceivable ar = accountsService.createReceivable(customerId, amount, dueDate, description);
            return ResponseEntity.ok(ar);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/receivables/collection")
    public ResponseEntity<?> registerCollection(@RequestBody Map<String, Object> req) {
        try {
            Long receivableId = Long.valueOf(req.get("receivableId").toString());
            Double amount = Double.valueOf(req.get("amount").toString());
            String method = req.get("method") != null ? req.get("method").toString() : null;
            String reference = req.get("reference") != null ? req.get("reference").toString() : null;
            Collection collection = accountsService.registerCollection(receivableId, amount, method, reference);
            return ResponseEntity.ok(collection);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/receivables/{id}/collections")
    public List<Collection> getCollectionsByReceivable(@PathVariable Long id) {
        return accountsService.getCollectionsByReceivable(id);
    }
    @GetMapping("/receivables/collections/date-range")
    public List<Collection> getCollectionsByDate(@RequestParam String start, @RequestParam String end) {
        return accountsService.getCollectionsByDate(LocalDate.parse(start), LocalDate.parse(end));
    }

    // --- Reportes ---
    @GetMapping("/payables/total-pending")
    public Double getTotalPayablesPending() {
        return accountsService.getTotalPayablesPending();
    }
    @GetMapping("/receivables/total-pending")
    public Double getTotalReceivablesPending() {
        return accountsService.getTotalReceivablesPending();
    }
}
