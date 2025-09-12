package gob.gt.com.lab.demo.repository.accounts;

import gob.gt.com.lab.demo.entity.accounts.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<Collection> findByAccountReceivableId(Long accountReceivableId);
    List<Collection> findByDateBetween(LocalDate start, LocalDate end);
}
