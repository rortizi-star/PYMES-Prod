package gob.gt.com.lab.demo.repository;

import gob.gt.com.lab.demo.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {}
