package hr.tis.academy.repository;

import hr.tis.academy.model.Product;
import hr.tis.academy.model.ProductsMetadata;
import hr.tis.academy.repository.exception.NoProductFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component("myProductRepositoryInMemory")
@Profile("memory")
public class ProductRepositoryInMemory implements ProductRepository {
    private static final List<ProductsMetadata> productsMetadataList = new CopyOnWriteArrayList<>();
    private static final AtomicLong idSequence = new AtomicLong();

    @Override
    public Long insertProducts(ProductsMetadata productsMetadata) {
        productsMetadata.setId(idSequence.incrementAndGet());
        productsMetadataList.add(productsMetadata);
        return productsMetadata.getId();
    }

    @Override
    public BigDecimal fetchSumOfPrices(LocalDate createdDate) throws NoProductFoundException {
        ProductsMetadata temp = getLatestProductsMetadataByDate(createdDate);

        List<Product> listaProizvoda = temp.getProducts();
        return listaProizvoda.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal fetchSumOfPrices(Long id) throws NoProductFoundException {
        List<Product> listaProizvoda = getProductsMetadataById(id).getProducts();
        return listaProizvoda.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public ProductsMetadata fetchProductsMetadata(LocalDate createdDate) throws NoProductFoundException {
        return getLatestProductsMetadataByDate(createdDate);
    }

    @Override
    public ProductsMetadata fetchProductsMetadata(Long id) throws NoProductFoundException {
        return getProductsMetadataById(id);
    }

    @Override
    public Integer fetchProductsMetadataCount() {
        return productsMetadataList.size();
    }

    @Override
    public List<ProductsMetadata> fetchAllProductsMetadataByDate(LocalDate date) {
        return productsMetadataList.stream().filter(p -> p.getCreatedTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    private ProductsMetadata getLatestProductsMetadataByDate(LocalDate createdDate) throws NoProductFoundException {
        List<ProductsMetadata> tempList = productsMetadataList.stream()
                .filter(p -> p.getCreatedTime().toLocalDate().isEqual(createdDate))
                .sorted(Comparator.comparing(p -> p.getCreatedTime().toLocalTime()))
                .collect(Collectors.toList());

        if (tempList.isEmpty()) {
            throw new NoProductFoundException("Record doesn't exist.");
        }

        return tempList.getLast();
    }

    private ProductsMetadata getProductsMetadataById(Long id) throws NoProductFoundException {
        List<ProductsMetadata> tempList = productsMetadataList.stream().filter(p -> p.getId().equals(id)).collect(Collectors.toList());

        if (tempList.isEmpty()) {
            throw new NoProductFoundException("Record doesn't exist.");
        }

        return tempList.getFirst();
    }
}
