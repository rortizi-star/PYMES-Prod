package gob.gt.com.lab.demo.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private String description;
    private Double balance;
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;
    @OneToMany(mappedBy = "bankAccount")
    private Set<BankTransaction> transactions;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
    public Bank getBank() { return bank; }
    public void setBank(Bank bank) { this.bank = bank; }
    public Set<BankTransaction> getTransactions() { return transactions; }
    public void setTransactions(Set<BankTransaction> transactions) { this.transactions = transactions; }
}
