package hr.tis.academy.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTS", schema = "PRODUCT_MANAGER")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private BigDecimal price;
    @Column
    private String unit;
    @Column
    private Integer grade = 0;

    @ManyToOne
    @JoinColumn(name = "PRODUCTS_METADATA_ID", nullable = false)
    private ProductsMetadata productsMetadata;

    public Product(String name, BigDecimal price, Integer grade, String unit) {
        this.name = name;
        this.price = price;
        this.grade = grade;
        this.unit = unit;
    }

    public Product() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", unit='" + unit + '\'' +
                ", grade=" + grade +
                '}';
    }
}
