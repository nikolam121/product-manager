package hr.tis.academy.common.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductsOrderSummaryDto(Long deliveryEmployeeId, String deliveryEmployeeName, String orderCode,
                                      BigDecimal totalAmount, LocalDate dueDate) {
}
