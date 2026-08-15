package hr.tis.academy.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PRODUCTS_METADATA", schema = "PRODUCT_MANAGER")
public class ProductsMetadata {
    @Column(name = "PRODUCTS_METADATA_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime createdTime;
    @Column
    private String title;
    @OneToMany(mappedBy = "productsMetadata", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products;

    public ProductsMetadata(Long id, LocalDateTime createdTime, String title, List<Product> products) {
        this.id = id;
        this.createdTime = createdTime;
        this.title = title;
        this.products = products;
    }

    public ProductsMetadata() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime datumVrijeme) {
        this.createdTime = datumVrijeme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String naslov) {
        this.title = naslov;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> popisProizvoda) {
        this.products = popisProizvoda;
    }

    @Override
    public String toString() {
        return "ProductsMetadata{" +
                "id=" + id +
                ", datumVrijeme=" + createdTime +
                ", naslov='" + title + '\'' +
                ", popisProizvoda=" + products +
                '}';
    }

}
