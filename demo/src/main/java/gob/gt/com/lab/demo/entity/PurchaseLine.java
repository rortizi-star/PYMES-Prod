package gob.gt.com.lab.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "purchase_lines")
public class PurchaseLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
    private Double price;
    private Double subtotal;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Purchase getPurchase() { return purchase; }
    public void setPurchase(Purchase purchase) { this.purchase = purchase; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
}
