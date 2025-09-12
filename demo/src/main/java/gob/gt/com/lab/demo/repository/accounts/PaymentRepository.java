package gob.gt.com.lab.demo.repository.accounts;

import gob.gt.com.lab.demo.entity.accounts.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByAccountPayableId(Long accountPayableId);
    List<Payment> findByDateBetween(LocalDate start, LocalDate end);
}
