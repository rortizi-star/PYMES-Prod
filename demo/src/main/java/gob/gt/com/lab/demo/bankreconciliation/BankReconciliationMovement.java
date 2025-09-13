package gob.gt.com.lab.demo.bankreconciliation;

import jakarta.persistence.*;

@Entity
public class BankReconciliationMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double amount;
    private String type; // DEBIT, CREDIT
    private String reference;
    private boolean matched;

    @ManyToOne
    @JoinColumn(name = "bank_reconciliation_id")
    private BankReconciliation bankReconciliation;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public boolean isMatched() {
        return matched;
    }
    public void setMatched(boolean matched) {
        this.matched = matched;
    }
    public BankReconciliation getBankReconciliation() {
        return bankReconciliation;
    }
    public void setBankReconciliation(BankReconciliation bankReconciliation) {
        this.bankReconciliation = bankReconciliation;
    }
}
