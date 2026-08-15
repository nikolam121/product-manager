package hr.tis.academy.repository;

import hr.tis.academy.model.ProductsOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsOrderRepository extends JpaRepository<ProductsOrder, Long> {
    List<ProductsOrder> findByActiveTrue();
}