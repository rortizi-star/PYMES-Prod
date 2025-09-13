package gob.gt.com.lab.demo.service.accounts;

import gob.gt.com.lab.demo.entity.accounts.AccountPayable;
import gob.gt.com.lab.demo.repository.accounts.AccountPayableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountPayableService {
    @Autowired
    private AccountPayableRepository payableRepository;

    public List<AccountPayable> findAll() {
        return payableRepository.findAll();
    }

    public Optional<AccountPayable> findById(Long id) {
        return payableRepository.findById(id);
    }

    public List<AccountPayable> findBySupplier(Long supplierId) {
        return payableRepository.findBySupplierId(supplierId);
    }

    public AccountPayable save(AccountPayable ap) {
        return payableRepository.save(ap);
    }

    public void delete(Long id) {
        payableRepository.deleteById(id);
    }
}
