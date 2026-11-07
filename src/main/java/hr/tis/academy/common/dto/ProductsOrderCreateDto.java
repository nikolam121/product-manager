package hr.tis.academy.common.dto;

import java.time.LocalDate;
import java.util.List;

public record ProductsOrderCreateDto(Long storeId, LocalDate dueDate, Long deliveryEmployeeId,
                                     List<ProductsOrderItemCreateDto> items) {
}
