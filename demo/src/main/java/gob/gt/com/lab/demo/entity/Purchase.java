package gob.gt.com.lab.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private LocalDate date;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseLine> lines;

    private Double total;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public List<PurchaseLine> getLines() { return lines; }
    public void setLines(List<PurchaseLine> lines) { this.lines = lines; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}
