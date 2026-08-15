package hr.tis.academy.service;

import hr.tis.academy.common.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> findAll();
    EmployeeDto findById(Long id);
    EmployeeDto save(EmployeeDto employeeDto);
    void deleteById(Long id);
    List<EmployeeDto> findByNameAndStartDate(String name, LocalDate startDate);
}