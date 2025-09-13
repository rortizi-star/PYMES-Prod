package gob.gt.com.lab.demo.repository.period;

import gob.gt.com.lab.demo.entity.period.AccountingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountingPeriodRepository extends JpaRepository<AccountingPeriod, Long> {
    List<AccountingPeriod> findByStatus(String status);
    AccountingPeriod findByName(String name);
}
