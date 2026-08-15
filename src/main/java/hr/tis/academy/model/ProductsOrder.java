package hr.tis.academy.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCTS_ORDER", schema = "PRODUCT_MANAGER")
public class ProductsOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STORE_ID", nullable = false)
    private Store store;

    @Column
    private LocalDateTime createdTime;

    @Column
    private String orderCode;

    @Column
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee deliveryEmployee;

    @Column
    private Boolean active = true;

    @OneToMany(mappedBy = "productsOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductsOrderItem> items = new ArrayList<>();

    public ProductsOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Employee getDeliveryEmployee() {
        return deliveryEmployee;
    }

    public void setDeliveryEmployee(Employee deliveryEmployee) {
        this.deliveryEmployee = deliveryEmployee;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<ProductsOrderItem> getItems() {
        return items;
    }

    public void setItems(List<ProductsOrderItem> items) {
        this.items = items;
    }
}