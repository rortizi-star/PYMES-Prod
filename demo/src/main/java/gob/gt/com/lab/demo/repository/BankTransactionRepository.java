package gob.gt.com.lab.demo.repository;

import gob.gt.com.lab.demo.entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {}
