package hr.tis.academy.model;

import jakarta.persistence.*;

import java.io.Serializable;



@Entity
@Table(name = "PRODUCTS_ORDER_ITEM", schema = "PRODUCT_MANAGER")
public class ProductsOrderItem implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCTS_ORDER_ID", nullable = false)
    private ProductsOrder productsOrder;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @Column
    private Integer quantity;

    public ProductsOrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductsOrder getProductsOrder() {
        return productsOrder;
    }

    public void setProductsOrder(ProductsOrder productsOrder) {
        this.productsOrder = productsOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}