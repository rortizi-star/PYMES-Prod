package gob.gt.com.lab.demo.repository.accounts;

import gob.gt.com.lab.demo.entity.accounts.AccountPayable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AccountPayableRepository extends JpaRepository<AccountPayable, Long> {
    List<AccountPayable> findBySupplierId(Long supplierId);
    List<AccountPayable> findByDueDateBetween(LocalDate start, LocalDate end);
    List<AccountPayable> findBySettled(boolean settled);
}
