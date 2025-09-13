package gob.gt.com.lab.demo.controller.accounts;

import gob.gt.com.lab.demo.entity.accounts.Payment;
import gob.gt.com.lab.demo.service.accounts.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts/payments")
public class PaymentController {
    @Autowired
    private PaymentService service;

    @GetMapping
    public List<Payment> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> findById(@PathVariable Long id) {
        Optional<Payment> payment = service.findById(id);
        return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountPayableId}")
    public List<Payment> findByAccountPayable(@PathVariable Long accountPayableId) {
        return service.findByAccountPayable(accountPayableId);
    }

    @PostMapping
    public Payment save(@RequestBody Payment payment) {
        return service.save(payment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
