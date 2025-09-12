package gob.gt.com.lab.demo.entity.accounts;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_payable_id")
    private AccountPayable accountPayable;

    private Double amount;
    private LocalDate date;
    private String method; // Efectivo, transferencia, cheque, etc.
    private String reference;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public AccountPayable getAccountPayable() { return accountPayable; }
    public void setAccountPayable(AccountPayable accountPayable) { this.accountPayable = accountPayable; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
}
