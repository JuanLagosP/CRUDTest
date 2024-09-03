package com.crudtest.service;

import com.crudtest.model.dto.EmployeeDto;
import com.crudtest.model.entity.Employee;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

public interface IEmployeeService {
    List<Employee> findAllEmployees();
    Employee findByEmployeeNumber(String employeeNumber);
    Employee saveEmployee(EmployeeDto employee) throws DuplicateKeyException;
    Employee updateEmployee(String employeeNumber, EmployeeDto employee) throws DuplicateKeyException;
    void deleteByEmployeeNumber(String employeeNumber);

    // For field validation purposes
    Boolean existsByEmployeeNumber(String employeeNumber);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);

}
