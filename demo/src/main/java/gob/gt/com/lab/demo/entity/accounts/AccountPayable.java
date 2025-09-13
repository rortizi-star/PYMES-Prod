package gob.gt.com.lab.demo.entity.accounts;

import gob.gt.com.lab.demo.entity.Supplier;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class AccountPayable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private Double amount;
    private Double balance;
    private LocalDate dueDate;
    private String description;

    private boolean settled;
    private String status;

    @OneToMany(mappedBy = "accountPayable")
    private List<Payment> payments;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }
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
    public List<Payment> getPayments() { return payments; }
    public void setPayments(List<Payment> payments) { this.payments = payments; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
