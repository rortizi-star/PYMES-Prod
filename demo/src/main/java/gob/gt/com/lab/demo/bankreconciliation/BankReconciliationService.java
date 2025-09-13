package gob.gt.com.lab.demo.bankreconciliation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BankReconciliationService {
    @Autowired
    private BankReconciliationRepository reconciliationRepository;
    @Autowired
    private BankReconciliationMovementRepository movementRepository;

    public List<BankReconciliation> findAll() {
        return reconciliationRepository.findAll();
    }

    public Optional<BankReconciliation> findById(Long id) {
        return reconciliationRepository.findById(id);
    }

    public BankReconciliation save(BankReconciliation reconciliation) {
        return reconciliationRepository.save(reconciliation);
    }

    public void delete(Long id) {
        reconciliationRepository.deleteById(id);
    }

    public BankReconciliationMovement saveMovement(BankReconciliationMovement movement) {
        return movementRepository.save(movement);
    }

    public List<BankReconciliationMovement> findMovementsByReconciliation(Long reconciliationId) {
        return movementRepository.findAll().stream()
            .filter(m -> m.getBankReconciliation() != null && m.getBankReconciliation().getId().equals(reconciliationId))
            .toList();
    }
}
