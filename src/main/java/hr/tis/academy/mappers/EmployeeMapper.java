package hr.tis.academy.mappers;

import hr.tis.academy.common.dto.*;
import hr.tis.academy.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = PositionMapper.class)
public interface EmployeeMapper {

    EmployeeDto toDto(Employee employee);

    @Mapping(ignore = true, target = "dateOfBirth")
    @Mapping(ignore = true, target = "oib")
    @Mapping(ignore = true, target = "address")
    @Mapping(ignore = true, target = "store")
    Employee toEntity(EmployeeDto employeeDto);

    List<EmployeeDto> toDtoList(List<Employee> employees);
}