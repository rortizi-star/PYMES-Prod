package gob.gt.com.lab.demo.repository;

import gob.gt.com.lab.demo.entity.PurchaseLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseLineRepository extends JpaRepository<PurchaseLine, Long> {
}
