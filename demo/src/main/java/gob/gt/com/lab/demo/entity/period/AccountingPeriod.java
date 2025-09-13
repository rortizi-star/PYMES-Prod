package gob.gt.com.lab.demo.entity.period;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class AccountingPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Ejemplo: "2025-01", "2025-02"
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // ABIERTO, CERRADO

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
