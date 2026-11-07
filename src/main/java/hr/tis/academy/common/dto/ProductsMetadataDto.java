package hr.tis.academy.common.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ProductsMetadataDto {

    private Long id;
    private LocalDateTime createdTime;
    private String title;
    private List<ProductDto> products;

    public ProductsMetadataDto(Long id, LocalDateTime createdTime, String title, List<ProductDto> products) {
        this.id = id;
        this.createdTime = createdTime;
        this.title = title;
        this.products = products;
    }

    public ProductsMetadataDto() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public LocalDateTime getCreatedTime() { return createdTime; }

    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public List<ProductDto> getProducts() { return products; }

    public void setProducts(List<ProductDto> products) { this.products = products; }

    @Override
    public String toString() {
        return "ProductsMetadata{id=" + id + ", datumVrijeme=" + createdTime + ", naslov='" + title + "', popisProizvoda=" + products + "}";
    }
}
