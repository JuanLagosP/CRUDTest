package com.crudtest.repository;

import com.crudtest.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmployeeNumber(String employeeNumber);
    void deleteByEmployeeNumber(String employeeNumber);

    // For field validation purposes
    Boolean existsByEmployeeNumber(String employeeNumber);
    Employee findByEmail(String email);
    Employee findByPhone(String phone);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
}
