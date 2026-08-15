package hr.tis.academy.controller;

import hr.tis.academy.common.dto.*;

import hr.tis.academy.service.ProductsOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "ProductsOrder", description = "Product order management")
@RequestMapping("/products-orders")
public class ProductsOrderController {

    private final ProductsOrderService productsOrderService;

    public ProductsOrderController(ProductsOrderService productsOrderService) {
        this.productsOrderService = productsOrderService;
    }

    @GetMapping
    public ResponseEntity<List<ProductsOrderDto>> getAll() {
        return ResponseEntity.ok(productsOrderService.findAll());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ProductsOrderDto> getById(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(productsOrderService.findById(orderId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProductsOrderDto>> getActive() {
        return ResponseEntity.ok(productsOrderService.findActive());
    }

    @PostMapping
    public ResponseEntity<ProductsOrderSummaryDto> create(@RequestBody ProductsOrderCreateDto createDto) {
        ProductsOrderSummaryDto summary = productsOrderService.save(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(summary);
    }

    @PatchMapping("/{orderId}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable("orderId") Long orderId) {
        productsOrderService.deactivate(orderId);
        return ResponseEntity.noContent().build();
    }
}