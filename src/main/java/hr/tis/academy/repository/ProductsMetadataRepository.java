package hr.tis.academy.repository;

import hr.tis.academy.model.ProductsMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductsMetadataRepository extends JpaRepository<ProductsMetadata, Long> {
    ProductsMetadata findByTitleAndCreatedTime(String title, LocalDateTime createdTime);

    @Query("select pm from ProductsMetadata pm where pm.title = :title and pm.createdTime = :createdTime")
    ProductsMetadata fetchByTitleAndCreatedTimeJPQL(String title, LocalDateTime createdTime);

    @Query(nativeQuery = true, value = "SELECT * FROM PRODUCT_MANAGER.PRODUCTS_METADATA pm WHERE pm.TITLE = :title AND pm.CREATED_TIME = :createdTime")
    ProductsMetadata fetchByTitleAndCreatedTimeNative(String title, LocalDateTime createdTime);


    @Query(nativeQuery = true, value = "SELECT * FROM PRODUCTS_METADATA WHERE PRODUCTS_METADATA.CREATED_TIME BETWEEN :startDate AND :endDate")
    List<ProductsMetadata> fetchProductsRecord(LocalDateTime startDate, LocalDateTime endDate);

}