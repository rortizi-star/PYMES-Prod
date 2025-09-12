package gob.gt.com.lab.demo.repository;

import gob.gt.com.lab.demo.entity.CashTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashTransactionRepository extends JpaRepository<CashTransaction, Long> {}
