package gob.gt.com.lab.demo.controller;

import gob.gt.com.lab.demo.entity.Account;
import gob.gt.com.lab.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Optional<Account> account = accountService.getAccountById(id);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
        Optional<Account> account = accountService.getAccountById(id);
        if (account.isPresent()) {
            Account a = account.get();
            a.setCode(accountDetails.getCode());
            a.setName(accountDetails.getName());
            a.setParent(accountDetails.getParent());
            a.setLevel(accountDetails.getLevel());
            a.setType(accountDetails.getType());
            a.setIsDetail(accountDetails.getIsDetail());
            a.setIsActive(accountDetails.getIsActive());
            a.setNature(accountDetails.getNature());
            return ResponseEntity.ok(accountService.saveAccount(a));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
