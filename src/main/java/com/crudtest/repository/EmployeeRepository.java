package com.crudtest.repository;

import com.crudtest.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmployeeNumber(String employeeNumber);

    // For field validation purposes
    Boolean existsByEmployeeNumber(String employeeNumber);
    Employee findByEmail(String email);
    Employee findByPhone(String phone);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);

    // For employee number generation
    @Query("SELECT MAX(e.employeeNumber) FROM Employee e")
    String findMaxEmployeeNumber();
}
