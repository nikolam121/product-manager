package hr.tis.academy.service;

import hr.tis.academy.common.dto.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ProductService {
    ProductsMetadataDto getFromWebTemp();
    void getFromWebIntoCollection();
    List<ProductDto> getByDate(LocalDate date);
    BigDecimal sumOfPrices(LocalDate date);

}