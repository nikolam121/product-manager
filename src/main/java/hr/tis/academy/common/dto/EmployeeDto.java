package hr.tis.academy.common.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeDto(Long id, String firstName, String lastName, LocalDate startDate, BigDecimal salary,
                          Integer vacationDays, PositionDto position) {
}
