package gob.gt.com.lab.demo.entity.inventory;

import gob.gt.com.lab.demo.entity.Product;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class InventoryMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    private LocalDate date;
    private Double quantity;
    private String type; // IN, OUT, TRANSFER
    private String reference; // e.g. purchase, sale, manual

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Warehouse getWarehouse() { return warehouse; }
    public void setWarehouse(Warehouse warehouse) { this.warehouse = warehouse; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
}
