package hr.tis.academy.repository;

import hr.tis.academy.model.Product;
import hr.tis.academy.model.ProductsMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepositoryJPA extends JpaRepository<Product, Long> {
    List<Product> findByNameAndGrade(String name, int grade);

    @Query("SELECT p FROM Product p WHERE p.name= :name AND p.grade= :grade")
    List<Product> fetchByNameAndGradeJPQL(String name, int grade);

    @Query(nativeQuery = true, value = "select * from PRODUCTS WHERE PRODUCTS.name = :name and PRODUCTS.grade = :grade")
    List<Product> fetchByNameAndGradeNative(String name, int grade);


    @Query("SELECT AVG(p.grade) FROM Product p WHERE p.productsMetadata.id = :metadataId")
    BigDecimal findAverageRatingAsBigDecimal(Long metadataId);

    @Query("SELECT AVG(p.grade) FROM Product p WHERE p.productsMetadata.id = :metadataId")
    Integer findAverageRatingAsInteger(Long metadataId);
}


