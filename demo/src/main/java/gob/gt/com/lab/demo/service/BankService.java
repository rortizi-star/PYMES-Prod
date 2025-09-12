package gob.gt.com.lab.demo.service;

import gob.gt.com.lab.demo.entity.*;
import gob.gt.com.lab.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    public List<Bank> getAllBanks() { return bankRepository.findAll(); }
    public Optional<Bank> getBankById(Long id) { return bankRepository.findById(id); }
    public Bank createBank(Bank bank) { return bankRepository.save(bank); }
    public Bank updateBank(Long id, Bank bankDetails) {
        return bankRepository.findById(id).map(bank -> {
            bank.setName(bankDetails.getName());
            bank.setDescription(bankDetails.getDescription());
            return bankRepository.save(bank);
        }).orElse(null);
    }
    public boolean deleteBank(Long id) {
        return bankRepository.findById(id).map(bank -> { bankRepository.delete(bank); return true; }).orElse(false);
    }

    // Métodos similares para cuentas y transacciones...
    public List<BankAccount> getAccountsByBank(Long bankId) {
        return bankAccountRepository.findAll().stream().filter(a -> a.getBank().getId().equals(bankId)).toList();
    }
    public List<BankTransaction> getTransactionsByAccount(Long accountId) {
        return bankTransactionRepository.findAll().stream().filter(t -> t.getBankAccount().getId().equals(accountId)).toList();
    }
    public Double getBalanceByAccount(Long accountId) {
        return bankAccountRepository.findById(accountId).map(BankAccount::getBalance).orElse(0.0);
    }
    public BankTransaction createTransaction(BankTransaction tx) {
        tx.setDate(LocalDateTime.now());
        BankAccount acc = tx.getBankAccount();
        if (acc != null && acc.getId() != null) {
            acc = bankAccountRepository.findById(acc.getId()).orElseThrow();
            acc.setBalance(acc.getBalance() + tx.getAmount());
            bankAccountRepository.save(acc);
        }
        return bankTransactionRepository.save(tx);
    }
}
