package hr.tis.academy.service.impl;

import hr.tis.academy.common.dto.*;

import hr.tis.academy.mappers.ProductsOrderMapper;
import hr.tis.academy.model.*;
import hr.tis.academy.repository.EmployeeRepository;
import hr.tis.academy.repository.ProductRepositoryJPA;
import hr.tis.academy.repository.ProductsOrderRepository;
import hr.tis.academy.service.ProductsOrderService;
import hr.tis.academy.service.exception.ResourceNotFoundException;
import hr.tis.academy.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductsOrderServiceImpl implements ProductsOrderService {

    private final ProductsOrderRepository productsOrderRepository;
    private final StoreRepository storeRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepositoryJPA productRepositoryJPA;

    public ProductsOrderServiceImpl(ProductsOrderRepository productsOrderRepository,
                                    StoreRepository storeRepository,
                                    EmployeeRepository employeeRepository,
                                    ProductRepositoryJPA productRepositoryJPA) {
        this.productsOrderRepository = productsOrderRepository;
        this.storeRepository = storeRepository;
        this.employeeRepository = employeeRepository;
        this.productRepositoryJPA = productRepositoryJPA;
    }

    @Override
    public List<ProductsOrderDto> findAll() {
        return ProductsOrderMapper.INSTANCE.toDtoList(productsOrderRepository.findAll());
    }

    @Override
    public ProductsOrderDto findById(Long id) {
        ProductsOrder order = productsOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductsOrder not found with id: " + id));
        return ProductsOrderMapper.INSTANCE.toDto(order);
    }

    @Override
    public List<ProductsOrderDto> findActive() {
        return ProductsOrderMapper.INSTANCE.toDtoList(productsOrderRepository.findByActiveTrue());
    }

    @Override
    @Transactional
    public ProductsOrderSummaryDto save(ProductsOrderCreateDto createDto) {
        Store store = storeRepository.findById(createDto.storeId())
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + createDto.storeId()));

        Employee deliveryEmployee = null;
        if (createDto.deliveryEmployeeId() != null) {
            deliveryEmployee = employeeRepository.findById(createDto.deliveryEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + createDto.deliveryEmployeeId()));
        }

        ProductsOrder order = new ProductsOrder();
        order.setStore(store);
        order.setDeliveryEmployee(deliveryEmployee);
        order.setCreatedTime(LocalDateTime.now());
        order.setOrderCode(UUID.randomUUID().toString());
        order.setDueDate(createDto.dueDate());
        order.setActive(true);

        List<ProductsOrderItem> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        List<ProductsOrderItemCreateDto> requestedItems =
                createDto.items() == null ? List.of() : createDto.items();

        for (ProductsOrderItemCreateDto itemDto : requestedItems) {
            Product product = productRepositoryJPA.findById(itemDto.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDto.productId()));

            ProductsOrderItem item = new ProductsOrderItem();
            item.setProductsOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDto.quantity());
            items.add(item);

            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(itemDto.quantity())));
        }
        order.setItems(items);

        ProductsOrder savedOrder = productsOrderRepository.save(order);

        return new ProductsOrderSummaryDto(
                deliveryEmployee != null ? deliveryEmployee.getId() : null,
                deliveryEmployee != null ? deliveryEmployee.getFirstName() : null,
                savedOrder.getOrderCode(),
                totalAmount,
                savedOrder.getDueDate()
        );
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        ProductsOrder order = productsOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductsOrder not found with id: " + id));
        order.setActive(false);
        productsOrderRepository.save(order);
    }
}