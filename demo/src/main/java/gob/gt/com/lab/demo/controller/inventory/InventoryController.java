package gob.gt.com.lab.demo.controller.inventory;

import gob.gt.com.lab.demo.entity.inventory.Inventory;
import gob.gt.com.lab.demo.entity.inventory.InventoryMovement;
import gob.gt.com.lab.demo.entity.inventory.Warehouse;
import gob.gt.com.lab.demo.service.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    // --- Inventario y almacenes ---
    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/warehouse/{warehouseId}")
    public List<Inventory> getInventoryByWarehouse(@PathVariable Long warehouseId) {
        return inventoryService.getInventoryByWarehouse(warehouseId);
    }

    @GetMapping("/product/{productId}")
    public List<Inventory> getInventoryByProduct(@PathVariable Long productId) {
        return inventoryService.getInventoryByProduct(productId);
    }

    @GetMapping("/warehouses")
    public List<Warehouse> getAllWarehouses() {
        return inventoryService.getAllWarehouses();
    }

    @PostMapping("/warehouses")
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return inventoryService.saveWarehouse(warehouse);
    }

    @DeleteMapping("/warehouses/{id}")
    public void deleteWarehouse(@PathVariable Long id) {
        inventoryService.deleteWarehouse(id);
    }

    // --- Movimientos de inventario ---
    @GetMapping("/movements")
    public List<InventoryMovement> getAllMovements() {
        return inventoryService.getAllMovements();
    }

    @GetMapping("/movements/date-range")
    public List<InventoryMovement> getMovementsByDateRange(@RequestParam String start, @RequestParam String end) {
        return inventoryService.getMovementsByDateRange(LocalDate.parse(start), LocalDate.parse(end));
    }

    @GetMapping("/movements/product/{productId}")
    public List<InventoryMovement> getMovementsByProduct(@PathVariable Long productId) {
        return inventoryService.getMovementsByProduct(productId);
    }

    @GetMapping("/movements/warehouse/{warehouseId}")
    public List<InventoryMovement> getMovementsByWarehouse(@PathVariable Long warehouseId) {
        return inventoryService.getMovementsByWarehouse(warehouseId);
    }

    @PostMapping("/movements")
    public ResponseEntity<?> registerMovement(@RequestBody Map<String, Object> req) {
        try {
            Long warehouseId = Long.valueOf(req.get("warehouseId").toString());
            Long productId = Long.valueOf(req.get("productId").toString());
            Double quantity = Double.valueOf(req.get("quantity").toString());
            String type = req.get("type").toString();
            String reference = req.get("reference") != null ? req.get("reference").toString() : null;
            InventoryMovement movement = inventoryService.registerMovement(warehouseId, productId, quantity, type, reference);
            return ResponseEntity.ok(movement);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/movements/transfer")
    public ResponseEntity<?> transferStock(@RequestBody Map<String, Object> req) {
        try {
            Long fromWarehouseId = Long.valueOf(req.get("fromWarehouseId").toString());
            Long toWarehouseId = Long.valueOf(req.get("toWarehouseId").toString());
            Long productId = Long.valueOf(req.get("productId").toString());
            Double quantity = Double.valueOf(req.get("quantity").toString());
            String reference = req.get("reference") != null ? req.get("reference").toString() : null;
            inventoryService.transferStock(fromWarehouseId, toWarehouseId, productId, quantity, reference);
            return ResponseEntity.ok("Transferencia realizada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // --- Auditoría y reportes ---
    @GetMapping("/audit")
    public List<InventoryMovement> auditMovements(@RequestParam(required = false) Long productId,
                                                  @RequestParam(required = false) Long warehouseId,
                                                  @RequestParam(required = false) String start,
                                                  @RequestParam(required = false) String end) {
        LocalDate startDate = start != null ? LocalDate.parse(start) : null;
        LocalDate endDate = end != null ? LocalDate.parse(end) : null;
        return inventoryService.auditMovements(productId, warehouseId, startDate, endDate);
    }
}
