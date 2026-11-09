package hr.tis.academy.service.impl;

import hr.tis.academy.common.dto.ProductsOrderCreateDto;
import hr.tis.academy.common.dto.ProductsOrderItemCreateDto;
import hr.tis.academy.common.dto.ProductsOrderSummaryDto;
import hr.tis.academy.model.Product;
import hr.tis.academy.model.ProductsOrder;
import hr.tis.academy.model.Store;
import hr.tis.academy.repository.EmployeeRepository;
import hr.tis.academy.repository.ProductRepositoryJPA;
import hr.tis.academy.repository.ProductsOrderRepository;
import hr.tis.academy.repository.StoreRepository;
import hr.tis.academy.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductsOrderServiceImplTest {

    private ProductsOrderRepository orders;
    private StoreRepository stores;
    private ProductRepositoryJPA products;
    private ProductsOrderServiceImpl service;

    @BeforeEach
    void setUp() {
        orders = mock(ProductsOrderRepository.class);
        stores = mock(StoreRepository.class);
        products = mock(ProductRepositoryJPA.class);
        service = new ProductsOrderServiceImpl(orders, stores, mock(EmployeeRepository.class), products);
        when(orders.save(any(ProductsOrder.class))).thenAnswer(call -> call.getArgument(0));
    }

    @Test
    void saveSumsPriceTimesQuantityForEveryItem() {
        when(stores.findById(1L)).thenReturn(Optional.of(new Store()));
        when(products.findById(10L)).thenReturn(Optional.of(product(new BigDecimal("1.20"))));
        when(products.findById(11L)).thenReturn(Optional.of(product(new BigDecimal("0.85"))));

        ProductsOrderCreateDto request = new ProductsOrderCreateDto(1L, LocalDate.of(2026, 9, 1), null,
                List.of(new ProductsOrderItemCreateDto(10L, 2), new ProductsOrderItemCreateDto(11L, 3)));

        ProductsOrderSummaryDto summary = service.save(request);

        assertEquals(new BigDecimal("4.95"), summary.totalAmount());
        assertEquals(LocalDate.of(2026, 9, 1), summary.dueDate());
    }

    @Test
    void saveWithoutItemsHasZeroTotal() {
        when(stores.findById(1L)).thenReturn(Optional.of(new Store()));

        ProductsOrderSummaryDto summary = service.save(new ProductsOrderCreateDto(1L, null, null, null));

        assertEquals(BigDecimal.ZERO, summary.totalAmount());
    }

    @Test
    void saveFailsWhenStoreDoesNotExist() {
        when(stores.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.save(new ProductsOrderCreateDto(99L, null, null, List.of())));
        verify(orders, never()).save(any());
    }

    @Test
    void deactivateMarksTheOrderInactive() {
        ProductsOrder order = new ProductsOrder();
        when(orders.findById(5L)).thenReturn(Optional.of(order));

        service.deactivate(5L);

        assertFalse(order.getActive());
        verify(orders).save(order);
    }

    @Test
    void deactivateFailsForUnknownOrder() {
        when(orders.findById(5L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deactivate(5L));
    }

    private static Product product(BigDecimal price) {
        Product product = new Product();
        product.setPrice(price);
        return product;
    }
}
