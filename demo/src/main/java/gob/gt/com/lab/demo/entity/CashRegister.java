package gob.gt.com.lab.demo.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class CashRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double balance;
    @OneToMany(mappedBy = "cashRegister")
    private Set<CashTransaction> transactions;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
    public Set<CashTransaction> getTransactions() { return transactions; }
    public void setTransactions(Set<CashTransaction> transactions) { this.transactions = transactions; }
}
