package gob.gt.com.lab.demo.bankreconciliation;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class BankReconciliation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reconciliationDate;
    private String bankAccount;
    private Double initialBalance;
    private Double finalBalance;
    private String status; // PENDING, COMPLETED

    @OneToMany(mappedBy = "bankReconciliation", cascade = CascadeType.ALL)
    private List<BankReconciliationMovement> movements;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getReconciliationDate() {
        return reconciliationDate;
    }
    public void setReconciliationDate(LocalDate reconciliationDate) {
        this.reconciliationDate = reconciliationDate;
    }
    public String getBankAccount() {
        return bankAccount;
    }
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
    public Double getInitialBalance() {
        return initialBalance;
    }
    public void setInitialBalance(Double initialBalance) {
        this.initialBalance = initialBalance;
    }
    public Double getFinalBalance() {
        return finalBalance;
    }
    public void setFinalBalance(Double finalBalance) {
        this.finalBalance = finalBalance;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<BankReconciliationMovement> getMovements() {
        return movements;
    }
    public void setMovements(List<BankReconciliationMovement> movements) {
        this.movements = movements;
    }
}
