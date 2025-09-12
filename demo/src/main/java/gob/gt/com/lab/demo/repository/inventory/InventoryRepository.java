package gob.gt.com.lab.demo.repository.inventory;

import gob.gt.com.lab.demo.entity.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByWarehouseId(Long warehouseId);
    List<Inventory> findByProductId(Long productId);
}
