package com.employeelist.dto;

public class EmployeeResponse {

    private Long id;
    private String name;
    private String email;
    private String department;
    private String phone;

    public EmployeeResponse() {
    }

    public EmployeeResponse(Long id, String name, String email, String department, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
