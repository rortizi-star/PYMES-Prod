package gob.gt.com.lab.demo.service.accounts;

import gob.gt.com.lab.demo.entity.accounts.Collection;
import gob.gt.com.lab.demo.repository.accounts.CollectionRepository;
import gob.gt.com.lab.demo.entity.accounts.AccountReceivable;
import gob.gt.com.lab.demo.repository.accounts.AccountReceivableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private AccountReceivableRepository accountReceivableRepository;

    public List<Collection> findAll() {
        return collectionRepository.findAll();
    }

    public Optional<Collection> findById(Long id) {
        return collectionRepository.findById(id);
    }

    public List<Collection> findByAccountReceivable(Long accountReceivableId) {
        return collectionRepository.findByAccountReceivableId(accountReceivableId);
    }

    public Collection save(Collection collection) {
        Collection saved = collectionRepository.save(collection);
        if (collection.getAccountReceivable() != null) {
            AccountReceivable ar = accountReceivableRepository.findById(collection.getAccountReceivable().getId()).orElse(null);
            if (ar != null) {
                double newBalance = ar.getBalance() - collection.getAmount();
                ar.setBalance(newBalance);
                if (newBalance <= 0) {
                    ar.setStatus("PAGADA");
                    ar.setBalance(0.0);
                }
                accountReceivableRepository.save(ar);
            }
        }
        return saved;
    }

    public void delete(Long id) {
        collectionRepository.deleteById(id);
    }
}
