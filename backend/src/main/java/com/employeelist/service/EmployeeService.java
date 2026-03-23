package com.employeelist.service;

import com.employeelist.dto.EmployeeRequest;
import com.employeelist.dto.EmployeeResponse;
import com.employeelist.model.Employee;
import com.employeelist.util.EmployeeMapper;
import com.employeelist.exception.ResourceNotFoundException;
import com.employeelist.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> findAll() {
        log.debug("Listing all employees");
        return employeeRepository.findAll().stream()
                .map(EmployeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeeResponse findById(Long id) {
        log.debug("Fetching employee id={}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return EmployeeMapper.toResponse(employee);
    }

    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        log.info("Creating employee email={}", request.getEmail());
        Employee employee = new Employee(
                request.getName(),
                request.getEmail(),
                request.getDepartment(),
                request.getPhone()
        );
        employee = employeeRepository.save(employee);
        return EmployeeMapper.toResponse(employee);
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        log.info("Updating employee id={}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setPhone(request.getPhone());
        employee = employeeRepository.save(employee);
        return EmployeeMapper.toResponse(employee);
    }

    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting employee id={}", id);
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
        log.info("Deleted employee id={}", id);
    }
}
