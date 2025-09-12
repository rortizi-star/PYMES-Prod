package gob.gt.com.lab.demo.repository.accounts;

import gob.gt.com.lab.demo.entity.accounts.AccountReceivable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AccountReceivableRepository extends JpaRepository<AccountReceivable, Long> {
    List<AccountReceivable> findByCustomerId(Long customerId);
    List<AccountReceivable> findByDueDateBetween(LocalDate start, LocalDate end);
    List<AccountReceivable> findBySettled(boolean settled);
}
