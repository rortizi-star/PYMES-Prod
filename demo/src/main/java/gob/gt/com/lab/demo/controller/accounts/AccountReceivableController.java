package gob.gt.com.lab.demo.controller.accounts;

import gob.gt.com.lab.demo.entity.accounts.AccountReceivable;
import gob.gt.com.lab.demo.service.accounts.AccountReceivableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts/receivable")
public class AccountReceivableController {
    @Autowired
    private AccountReceivableService service;

    @GetMapping
    public List<AccountReceivable> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountReceivable> findById(@PathVariable Long id) {
        Optional<AccountReceivable> ar = service.findById(id);
        return ar.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public List<AccountReceivable> findByCustomer(@PathVariable Long customerId) {
        return service.findByCustomer(customerId);
    }

    @PostMapping
    public AccountReceivable save(@RequestBody AccountReceivable ar) {
        return service.save(ar);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
