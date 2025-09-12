package gob.gt.com.lab.demo.entity.accounts;

import gob.gt.com.lab.demo.entity.Customer;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class AccountReceivable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Double amount;
    private Double balance;
    private LocalDate dueDate;
    private String description;
    private boolean settled;

    @OneToMany(mappedBy = "accountReceivable")
    private List<Collection> collections;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isSettled() { return settled; }
    public void setSettled(boolean settled) { this.settled = settled; }
    public List<Collection> getCollections() { return collections; }
    public void setCollections(List<Collection> collections) { this.collections = collections; }
}
