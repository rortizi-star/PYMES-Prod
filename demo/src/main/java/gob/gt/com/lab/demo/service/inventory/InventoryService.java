package gob.gt.com.lab.demo.service.inventory;

import gob.gt.com.lab.demo.entity.inventory.Inventory;
import gob.gt.com.lab.demo.entity.inventory.InventoryMovement;
import gob.gt.com.lab.demo.entity.inventory.Warehouse;
import gob.gt.com.lab.demo.entity.Product;
import gob.gt.com.lab.demo.repository.inventory.InventoryRepository;
import gob.gt.com.lab.demo.repository.inventory.InventoryMovementRepository;
import gob.gt.com.lab.demo.repository.inventory.WarehouseRepository;
import gob.gt.com.lab.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryMovementRepository movementRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public List<Inventory> getInventoryByWarehouse(Long warehouseId) {
        return inventoryRepository.findByWarehouseId(warehouseId);
    }

    public List<Inventory> getInventoryByProduct(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }

    public Optional<Inventory> getInventory(Long id) {
        return inventoryRepository.findById(id);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Optional<Warehouse> getWarehouse(Long id) {
        return warehouseRepository.findById(id);
    }

    public Warehouse saveWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    public List<InventoryMovement> getAllMovements() {
        return movementRepository.findAll();
    }

    public List<InventoryMovement> getMovementsByDateRange(LocalDate start, LocalDate end) {
        return movementRepository.findByDateBetween(start, end);
    }

    public List<InventoryMovement> getMovementsByProduct(Long productId) {
        return movementRepository.findByProductId(productId);
    }

    public List<InventoryMovement> getMovementsByWarehouse(Long warehouseId) {
        return movementRepository.findByWarehouseId(warehouseId);
    }

    @Transactional
    public InventoryMovement registerMovement(Long warehouseId, Long productId, Double quantity, String type, String reference) throws Exception {
        if (quantity == null || quantity == 0) throw new Exception("Cantidad inválida");
        if (!type.equals("IN") && !type.equals("OUT") && !type.equals("TRANSFER")) throw new Exception("Tipo de movimiento inválido");
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() -> new Exception("Almacén no encontrado"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("Producto no encontrado"));
        Inventory inventory = inventoryRepository.findByWarehouseId(warehouseId).stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst().orElse(null);
        if (inventory == null) {
            inventory = new Inventory();
            inventory.setWarehouse(warehouse);
            inventory.setProduct(product);
            inventory.setQuantity(0.0);
        }
        double newQty = inventory.getQuantity();
        if (type.equals("IN")) {
            newQty += quantity;
        } else if (type.equals("OUT")) {
            if (inventory.getQuantity() < quantity) throw new Exception("Stock insuficiente");
            newQty -= quantity;
        } else if (type.equals("TRANSFER")) {
            // Para transferencias, se debe manejar en el controlador (orquestar dos movimientos)
            throw new Exception("Use el endpoint de transferencia para este tipo de movimiento");
        }
        inventory.setQuantity(newQty);
        inventoryRepository.save(inventory);
        InventoryMovement movement = new InventoryMovement();
        movement.setInventory(inventory);
        movement.setWarehouse(warehouse);
        movement.setProduct(product);
        movement.setDate(LocalDate.now());
        movement.setQuantity(quantity);
        movement.setType(type);
        movement.setReference(reference);
        return movementRepository.save(movement);
    }

    @Transactional
    public void transferStock(Long fromWarehouseId, Long toWarehouseId, Long productId, Double quantity, String reference) throws Exception {
        // OUT del almacén origen
        registerMovement(fromWarehouseId, productId, quantity, "OUT", "TRANSFERENCIA A " + toWarehouseId + (reference != null ? (" - " + reference) : ""));
        // IN al almacén destino
        registerMovement(toWarehouseId, productId, quantity, "IN", "TRANSFERENCIA DE " + fromWarehouseId + (reference != null ? (" - " + reference) : ""));
    }

    // Auditoría: obtener historial de movimientos por producto, almacén o fecha
    public List<InventoryMovement> auditMovements(Long productId, Long warehouseId, LocalDate start, LocalDate end) {
        if (productId != null) return getMovementsByProduct(productId);
        if (warehouseId != null) return getMovementsByWarehouse(warehouseId);
        if (start != null && end != null) return getMovementsByDateRange(start, end);
        return getAllMovements();
    }
}
