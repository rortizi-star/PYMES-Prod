package gob.gt.com.lab.demo.service.accounts;

import gob.gt.com.lab.demo.entity.accounts.AccountReceivable;
import gob.gt.com.lab.demo.repository.accounts.AccountReceivableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountReceivableService {
    @Autowired
    private AccountReceivableRepository receivableRepository;

    public List<AccountReceivable> findAll() {
        return receivableRepository.findAll();
    }

    public Optional<AccountReceivable> findById(Long id) {
        return receivableRepository.findById(id);
    }

    public List<AccountReceivable> findByCustomer(Long customerId) {
        return receivableRepository.findByCustomerId(customerId);
    }

    public AccountReceivable save(AccountReceivable ar) {
        return receivableRepository.save(ar);
    }

    public void delete(Long id) {
        receivableRepository.deleteById(id);
    }
}
