package hr.tis.academy.service.impl;

import hr.tis.academy.common.dto.*;

import hr.tis.academy.mappers.EmployeeMapper;
import hr.tis.academy.model.Employee;
import hr.tis.academy.repository.EmployeeRepository;
import hr.tis.academy.service.EmployeeService;
import hr.tis.academy.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<EmployeeDto> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.toDtoList(employees);
    }

    @Override
    public EmployeeDto findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional
    public EmployeeDto save(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(savedEmployee);
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDto> findByNameAndStartDate(String name, LocalDate startDate) {
        List<Employee> employees = employeeRepository.findByFirstNameAndStartDate(name, startDate);
        return employeeMapper.toDtoList(employees);
    }
}