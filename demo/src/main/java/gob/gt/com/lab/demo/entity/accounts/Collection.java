package gob.gt.com.lab.demo.entity.accounts;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_receivable_id")
    private AccountReceivable accountReceivable;

    private Double amount;
    private LocalDate date;
    private String method; // Efectivo, transferencia, cheque, etc.
    private String reference;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public AccountReceivable getAccountReceivable() { return accountReceivable; }
    public void setAccountReceivable(AccountReceivable accountReceivable) { this.accountReceivable = accountReceivable; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
}
