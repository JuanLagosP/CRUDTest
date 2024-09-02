package com.crudtest.repository;

import com.crudtest.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmployeeNumber(String employeeNumber);
    void deleteByEmployeeNumber(String employeeNumber);
}
