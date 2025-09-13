package gob.gt.com.lab.demo.controller.accounts;

import gob.gt.com.lab.demo.entity.accounts.Collection;
import gob.gt.com.lab.demo.service.accounts.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts/collections")
public class CollectionController {
    @Autowired
    private CollectionService service;

    @GetMapping
    public List<Collection> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collection> findById(@PathVariable Long id) {
        Optional<Collection> collection = service.findById(id);
        return collection.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountReceivableId}")
    public List<Collection> findByAccountReceivable(@PathVariable Long accountReceivableId) {
        return service.findByAccountReceivable(accountReceivableId);
    }

    @PostMapping
    public Collection save(@RequestBody Collection collection) {
        return service.save(collection);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
