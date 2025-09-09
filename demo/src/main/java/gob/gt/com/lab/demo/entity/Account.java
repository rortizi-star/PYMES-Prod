package gob.gt.com.lab.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Account parent;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private String type; // Asset, Liability, Equity, Income, Expense

    @Column(nullable = false)
    private Boolean isDetail = true;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private String nature; // Debit, Credit

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Account getParent() { return parent; }
    public void setParent(Account parent) { this.parent = parent; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Boolean getIsDetail() { return isDetail; }
    public void setIsDetail(Boolean isDetail) { this.isDetail = isDetail; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public String getNature() { return nature; }
    public void setNature(String nature) { this.nature = nature; }
}
