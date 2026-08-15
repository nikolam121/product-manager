package hr.tis.academy.controller;

import hr.tis.academy.common.dto.*;
import hr.tis.academy.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "Products", description = "Product management")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/fetch")
    public ProductsMetadataDto fetchFromWeb() {
        return productService.getFromWebTemp();
    }

    @PostMapping("/products/save")
    public void saveFromWeb() {
        productService.getFromWebIntoCollection();
    }

    @GetMapping("/products")
    public List<ProductDto> getByDate(@RequestParam LocalDate date) {
        return productService.getByDate(date);
    }

    @GetMapping("/products/sum")
    public BigDecimal sumOfPrices(@RequestParam LocalDate date) {
        return productService.sumOfPrices(date);
    }

}