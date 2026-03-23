package com.employeelist.service;

import com.employeelist.dto.EmployeeRequest;
import com.employeelist.dto.EmployeeResponse;
import com.employeelist.exception.ResourceNotFoundException;
import com.employeelist.model.Employee;
import com.employeelist.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee existing;

    @BeforeEach
    void setUp() {
        existing = new Employee("Jane", "jane@example.com", "Eng", "111");
        existing.setId(1L);
    }

    @Test
    void findAll_returnsMappedList() {
        when(employeeRepository.findAll()).thenReturn(List.of(existing));

        List<EmployeeResponse> result = employeeService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("jane@example.com");
    }

    @Test
    void findById_returnsEmployee_whenExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));

        EmployeeResponse r = employeeService.findById(1L);

        assertThat(r.getName()).isEqualTo("Jane");
    }

    @Test
    void findById_throws_whenMissing() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void create_savesAndReturnsResponse() {
        EmployeeRequest req = new EmployeeRequest("New", "new@example.com", "HR", "222");
        Employee saved = new Employee("New", "new@example.com", "HR", "222");
        saved.setId(5L);
        when(employeeRepository.save(any(Employee.class))).thenReturn(saved);

        EmployeeResponse r = employeeService.create(req);

        assertThat(r.getId()).isEqualTo(5L);
        assertThat(r.getEmail()).isEqualTo("new@example.com");
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void update_modifiesAndReturns_whenExists() {
        EmployeeRequest req = new EmployeeRequest("Updated", "jane@example.com", "Product", "333");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(inv -> inv.getArgument(0));

        EmployeeResponse r = employeeService.update(1L, req);

        assertThat(r.getName()).isEqualTo("Updated");
        assertThat(r.getDepartment()).isEqualTo("Product");
    }

    @Test
    void update_throws_whenMissing() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.update(2L, new EmployeeRequest("X", "x@x.com", null, null)))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteById_removes_whenExists() {
        when(employeeRepository.existsById(1L)).thenReturn(true);

        employeeService.deleteById(1L);

        verify(employeeRepository).deleteById(1L);
    }

    @Test
    void deleteById_throws_whenMissing() {
        when(employeeRepository.existsById(9L)).thenReturn(false);

        assertThatThrownBy(() -> employeeService.deleteById(9L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
