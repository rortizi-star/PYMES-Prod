package gob.gt.com.lab.demo.repository;

import gob.gt.com.lab.demo.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {}
