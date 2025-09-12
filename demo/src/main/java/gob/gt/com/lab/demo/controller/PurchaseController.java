package gob.gt.com.lab.demo.controller;

import gob.gt.com.lab.demo.entity.Purchase;
import gob.gt.com.lab.demo.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @GetMapping
    public List<Purchase> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable Long id) {
        return purchaseService.getPurchaseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Purchase createPurchase(@RequestBody Purchase purchase) {
        return purchaseService.createPurchase(purchase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable Long id, @RequestBody Purchase purchase) {
        Purchase updated = purchaseService.updatePurchase(id, purchase);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
        if (purchaseService.deletePurchase(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Reporte: compras por rango de fechas
    @GetMapping("/report/date-range")
    public List<Purchase> getPurchasesByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return purchaseService.getPurchasesByDateRange(start, end);
    }

    // Reporte: total de compras por proveedor
    @GetMapping("/report/total-by-supplier/{supplierId}")
    public Double getTotalPurchasesBySupplier(@PathVariable Long supplierId) {
        return purchaseService.getTotalPurchasesBySupplier(supplierId);
    }
}
