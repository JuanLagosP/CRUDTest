package com.crudtest.service;

import com.crudtest.model.dto.EmployeeDto;
import com.crudtest.model.entity.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> findAllEmployees();
    Employee findByEmployeeNumber(String employeeNumber);
    Employee saveEmployee(EmployeeDto employee);
    Employee updateEmployee(String employeeNumber, EmployeeDto employee);
    void deleteByEmployeeNumber(String employeeNumber);

}
