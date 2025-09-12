package gob.gt.com.lab.demo.service;

import gob.gt.com.lab.demo.entity.*;
import gob.gt.com.lab.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CashRegisterService {
    @Autowired
    private CashRegisterRepository cashRegisterRepository;
    @Autowired
    private CashTransactionRepository cashTransactionRepository;

    public List<CashRegister> getAllCashRegisters() { return cashRegisterRepository.findAll(); }
    public Optional<CashRegister> getCashRegisterById(Long id) { return cashRegisterRepository.findById(id); }
    public CashRegister createCashRegister(CashRegister cr) { return cashRegisterRepository.save(cr); }
    public CashRegister updateCashRegister(Long id, CashRegister crDetails) {
        return cashRegisterRepository.findById(id).map(cr -> {
            cr.setName(crDetails.getName());
            cr.setDescription(crDetails.getDescription());
            return cashRegisterRepository.save(cr);
        }).orElse(null);
    }
    public boolean deleteCashRegister(Long id) {
        return cashRegisterRepository.findById(id).map(cr -> { cashRegisterRepository.delete(cr); return true; }).orElse(false);
    }

    public List<CashTransaction> getTransactionsByRegister(Long registerId) {
        return cashTransactionRepository.findAll().stream().filter(t -> t.getCashRegister().getId().equals(registerId)).toList();
    }
    public Double getBalanceByRegister(Long registerId) {
        return cashRegisterRepository.findById(registerId).map(CashRegister::getBalance).orElse(0.0);
    }
    public CashTransaction createTransaction(CashTransaction tx) {
        tx.setDate(LocalDateTime.now());
        CashRegister cr = tx.getCashRegister();
        if (cr != null && cr.getId() != null) {
            cr = cashRegisterRepository.findById(cr.getId()).orElseThrow();
            cr.setBalance(cr.getBalance() + tx.getAmount());
            cashRegisterRepository.save(cr);
        }
        return cashTransactionRepository.save(tx);
    }
}
