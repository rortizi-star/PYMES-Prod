
package gob.gt.com.lab.demo.service.period;

import gob.gt.com.lab.demo.entity.period.AccountingPeriod;
import gob.gt.com.lab.demo.repository.period.AccountingPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountingPeriodService {
    @Autowired
    private AccountingPeriodRepository periodRepository;

    public List<AccountingPeriod> findAll() {
        return periodRepository.findAll();
    }

    public Optional<AccountingPeriod> findById(Long id) {
        return periodRepository.findById(id);
    }

    public AccountingPeriod save(AccountingPeriod period) {
        return periodRepository.save(period);
    }

    public void delete(Long id) {
        periodRepository.deleteById(id);
    }

    public List<AccountingPeriod> findByStatus(String status) {
        return periodRepository.findByStatus(status);
    }

    public AccountingPeriod findByName(String name) {
        return periodRepository.findByName(name);
    }

    public AccountingPeriod closePeriod(Long id) throws Exception {
        AccountingPeriod period = periodRepository.findById(id).orElseThrow(() -> new Exception("Período no encontrado"));
        period.setStatus("CERRADO");
        return periodRepository.save(period);
    }

    public AccountingPeriod openPeriod(Long id) throws Exception {
        AccountingPeriod period = periodRepository.findById(id).orElseThrow(() -> new Exception("Período no encontrado"));
        period.setStatus("ABIERTO");
        return periodRepository.save(period);
    }
}
