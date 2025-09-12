package gob.gt.com.lab.demo.service;

import gob.gt.com.lab.demo.entity.Purchase;
import gob.gt.com.lab.demo.entity.PurchaseLine;
import gob.gt.com.lab.demo.repository.PurchaseRepository;
import gob.gt.com.lab.demo.repository.PurchaseLineRepository;
import gob.gt.com.lab.demo.repository.SupplierRepository;
import gob.gt.com.lab.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private PurchaseLineRepository purchaseLineRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Optional<Purchase> getPurchaseById(Long id) {
        return purchaseRepository.findById(id);
    }

    public Purchase createPurchase(Purchase purchase) {
        // Validar proveedor
        if (purchase.getSupplier() == null || purchase.getSupplier().getId() == null) {
            throw new IllegalArgumentException("El proveedor es obligatorio");
        }
        if (!supplierRepository.existsById(purchase.getSupplier().getId())) {
            throw new IllegalArgumentException("El proveedor no existe");
        }
        // Validar líneas
        if (purchase.getLines() == null || purchase.getLines().isEmpty()) {
            throw new IllegalArgumentException("Debe agregar al menos una línea de producto");
        }
        double total = 0.0;
        for (PurchaseLine line : purchase.getLines()) {
            if (line.getProduct() == null || line.getProduct().getId() == null) {
                throw new IllegalArgumentException("Cada línea debe tener un producto válido");
            }
            if (!productRepository.existsById(line.getProduct().getId())) {
                throw new IllegalArgumentException("El producto con ID " + line.getProduct().getId() + " no existe");
            }
            if (line.getQuantity() == null || line.getQuantity() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
            }
            if (line.getPrice() == null || line.getPrice() <= 0) {
                throw new IllegalArgumentException("El precio debe ser mayor a cero");
            }
            line.setPurchase(purchase);
            line.setSubtotal(line.getPrice() * line.getQuantity());
            total += line.getSubtotal();
        }
        purchase.setTotal(total);
        Purchase savedPurchase = purchaseRepository.save(purchase);
        for (PurchaseLine line : purchase.getLines()) {
            purchaseLineRepository.save(line);
        }
        return savedPurchase;
    }

    public Purchase updatePurchase(Long id, Purchase purchaseDetails) {
        return purchaseRepository.findById(id).map(purchase -> {
            purchase.setSupplier(purchaseDetails.getSupplier());
            purchase.setDate(purchaseDetails.getDate());
            purchase.getLines().clear();
            double total = 0.0;
            if (purchaseDetails.getLines() != null) {
                for (PurchaseLine line : purchaseDetails.getLines()) {
                    line.setPurchase(purchase);
                    line.setSubtotal(line.getPrice() * line.getQuantity());
                    total += line.getSubtotal();
                    purchase.getLines().add(line);
                }
            }
            purchase.setTotal(total);
            return purchaseRepository.save(purchase);
        }).orElse(null);
    }

    public boolean deletePurchase(Long id) {
        return purchaseRepository.findById(id).map(purchase -> {
            purchaseRepository.delete(purchase);
            return true;
        }).orElse(false);
    }

    public List<Purchase> getPurchasesByDateRange(LocalDate start, LocalDate end) {
        return purchaseRepository.findAll().stream()
            .filter(p -> p.getDate() != null &&
                !p.getDate().isBefore(start) && !p.getDate().isAfter(end))
            .toList();
    }

    public Double getTotalPurchasesBySupplier(Long supplierId) {
        return purchaseRepository.findAll().stream()
            .filter(p -> p.getSupplier() != null && p.getSupplier().getId().equals(supplierId))
            .mapToDouble(p -> p.getTotal() != null ? p.getTotal() : 0.0)
            .sum();
    }
}
