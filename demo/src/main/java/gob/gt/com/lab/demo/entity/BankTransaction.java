package gob.gt.com.lab.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BankTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private Double amount;
    private String description;
    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BankAccount getBankAccount() { return bankAccount; }
    public void setBankAccount(BankAccount bankAccount) { this.bankAccount = bankAccount; }
}
