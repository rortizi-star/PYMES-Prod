package gob.gt.com.lab.demo.bankreconciliation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankReconciliationMovementRepository extends JpaRepository<BankReconciliationMovement, Long> {
}
