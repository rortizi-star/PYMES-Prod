package gob.gt.com.lab.demo.repository.inventory;

import gob.gt.com.lab.demo.entity.inventory.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
