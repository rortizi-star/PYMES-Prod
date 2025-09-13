package gob.gt.com.lab.demo.service.accounts;

import gob.gt.com.lab.demo.entity.accounts.Payment;
import gob.gt.com.lab.demo.repository.accounts.PaymentRepository;
import gob.gt.com.lab.demo.entity.accounts.AccountPayable;
import gob.gt.com.lab.demo.repository.accounts.AccountPayableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private AccountPayableRepository accountPayableRepository;

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> findByAccountPayable(Long accountPayableId) {
        return paymentRepository.findByAccountPayableId(accountPayableId);
    }

    public Payment save(Payment payment) {
        Payment saved = paymentRepository.save(payment);
        if (payment.getAccountPayable() != null) {
            AccountPayable ap = accountPayableRepository.findById(payment.getAccountPayable().getId()).orElse(null);
            if (ap != null) {
                double newBalance = ap.getBalance() - payment.getAmount();
                ap.setBalance(newBalance);
                if (newBalance <= 0) {
                    ap.setStatus("PAGADA");
                    ap.setBalance(0.0);
                }
                accountPayableRepository.save(ap);
            }
        }
        return saved;
    }

    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }
}
