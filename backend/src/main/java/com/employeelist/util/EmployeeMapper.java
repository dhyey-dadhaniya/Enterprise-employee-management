package com.employeelist.util;

import com.employeelist.dto.EmployeeResponse;
import com.employeelist.model.Employee;

public final class EmployeeMapper {

    private EmployeeMapper() {
    }

    public static EmployeeResponse toResponse(Employee employee) {
        if (employee == null) {
            return null;
        }
        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getPhone()
        );
    }
}
