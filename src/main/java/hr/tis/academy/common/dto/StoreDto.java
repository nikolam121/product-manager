package hr.tis.academy.common.dto;

import java.util.List;

public record StoreDto(Long id, String storeName, AddressDto address, String telephoneNumber, String email,
                       List<WorkingDayDto> workingDays, List<EmployeeDto> employees) {
}
