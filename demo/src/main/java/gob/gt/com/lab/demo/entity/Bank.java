package gob.gt.com.lab.demo.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "bank")
    private Set<BankAccount> accounts;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Set<BankAccount> getAccounts() { return accounts; }
    public void setAccounts(Set<BankAccount> accounts) { this.accounts = accounts; }
}
