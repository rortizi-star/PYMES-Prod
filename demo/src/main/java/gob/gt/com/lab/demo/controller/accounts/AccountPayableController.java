package gob.gt.com.lab.demo.controller.accounts;

import gob.gt.com.lab.demo.entity.accounts.AccountPayable;
import gob.gt.com.lab.demo.service.accounts.AccountPayableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts/payable")
public class AccountPayableController {
    @Autowired
    private AccountPayableService service;

    @GetMapping
    public List<AccountPayable> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountPayable> findById(@PathVariable Long id) {
        Optional<AccountPayable> ap = service.findById(id);
        return ap.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/supplier/{supplierId}")
    public List<AccountPayable> findBySupplier(@PathVariable Long supplierId) {
        return service.findBySupplier(supplierId);
    }

    @PostMapping
    public AccountPayable save(@RequestBody AccountPayable ap) {
        return service.save(ap);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
