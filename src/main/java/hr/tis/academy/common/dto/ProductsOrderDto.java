package hr.tis.academy.common.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ProductsOrderDto(Long id, Long storeId, String storeName, LocalDateTime createdTime, String orderCode,
                               LocalDate dueDate, Long deliveryEmployeeId, String deliveryEmployeeName,
                               Boolean active, List<ProductsOrderItemDto> items) {
}
