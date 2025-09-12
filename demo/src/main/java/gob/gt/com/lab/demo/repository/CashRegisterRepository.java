package gob.gt.com.lab.demo.repository;

import gob.gt.com.lab.demo.entity.CashRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashRegisterRepository extends JpaRepository<CashRegister, Long> {}
