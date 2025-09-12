package gob.gt.com.lab.demo.repository.inventory;

import gob.gt.com.lab.demo.entity.inventory.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {
    List<InventoryMovement> findByDateBetween(LocalDate start, LocalDate end);
    List<InventoryMovement> findByProductId(Long productId);
    List<InventoryMovement> findByWarehouseId(Long warehouseId);
}
