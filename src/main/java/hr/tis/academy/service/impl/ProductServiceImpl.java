package hr.tis.academy.service.impl;

import hr.tis.academy.common.dto.ProductDto;
import hr.tis.academy.common.dto.ProductsMetadataDto;
import hr.tis.academy.mappers.ProductsMetadataMapper;
import hr.tis.academy.model.ProductsMetadata;
import hr.tis.academy.repository.ProductRepository;
import hr.tis.academy.scraper.WebScraper;
import hr.tis.academy.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final WebScraper webScraper;

    public ProductServiceImpl(ProductRepository productRepository, WebScraper webScraper) {
        this.productRepository = productRepository;
        this.webScraper = webScraper;
    }

    @Override
    public ProductsMetadataDto getFromWebTemp() {
        return ProductsMetadataMapper.INSTANCE.toDto(webScraper.fetchProducts());
    }

    @Override
    public void getFromWebIntoCollection() {
        productRepository.insertProducts(webScraper.fetchProducts());
    }

    @Override
    public List<ProductDto> getByDate(LocalDate date) {
        return productRepository.fetchAllProductsMetadataByDate(date).stream()
                .map(ProductsMetadataMapper.INSTANCE::toDto)
                .map(ProductsMetadataDto::getProducts)
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public BigDecimal sumOfPrices(LocalDate date) {
        return productRepository.fetchSumOfPrices(date);
    }
}
