package gob.gt.com.lab.demo.controller.period;

import gob.gt.com.lab.demo.entity.period.AccountingPeriod;
import gob.gt.com.lab.demo.service.period.AccountingPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/periods")
public class AccountingPeriodController {
    @Autowired
    private AccountingPeriodService service;

    @GetMapping
    public List<AccountingPeriod> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountingPeriod> findById(@PathVariable Long id) {
        Optional<AccountingPeriod> period = service.findById(id);
        return period.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AccountingPeriod save(@RequestBody AccountingPeriod period) {
        return service.save(period);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/status/{status}")
    public List<AccountingPeriod> findByStatus(@PathVariable String status) {
        return service.findByStatus(status);
    }

    @GetMapping("/name/{name}")
    public AccountingPeriod findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<?> closePeriod(@PathVariable Long id) {
        try {
            AccountingPeriod period = service.closePeriod(id);
            return ResponseEntity.ok(period);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/open")
    public ResponseEntity<?> openPeriod(@PathVariable Long id) {
        try {
            AccountingPeriod period = service.openPeriod(id);
            return ResponseEntity.ok(period);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
