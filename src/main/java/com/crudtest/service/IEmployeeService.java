package com.crudtest.service;

import com.crudtest.model.dto.EmployeeDto;
import com.crudtest.model.dto.SearchInfoDto;
import com.crudtest.model.entity.Employee;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEmployeeService {
    Page<Employee> findAllEmployees(int page, int size);
    Employee findByEmployeeNumber(String employeeNumber);
    Employee saveEmployee(EmployeeDto employee) throws DuplicateKeyException;
    Employee updateEmployee(String employeeNumber, EmployeeDto employee) throws DuplicateKeyException;
    void deleteByEmployeeNumber(String employeeNumber);

    // For field validation purposes
    Boolean existsByEmployeeNumber(String employeeNumber);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);

    // For search
    List<Employee> findByNumberOrNameOrSurname(String searchVal);

}
