package hr.tis.academy.service;

import hr.tis.academy.common.dto.*;


import java.util.List;

public interface ProductsOrderService {
    List<ProductsOrderDto> findAll();

    ProductsOrderDto findById(Long id);

    List<ProductsOrderDto> findActive();

    ProductsOrderSummaryDto save(ProductsOrderCreateDto createDto);

    void deactivate(Long id);
}