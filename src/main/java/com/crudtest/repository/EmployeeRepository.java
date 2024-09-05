package com.crudtest.repository;

import com.crudtest.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e ORDER BY e.employeeNumber DESC")
    Page<Employee> findAllOrderedByEmployeeNumber(Pageable pageable);
    Employee findByEmployeeNumber(String employeeNumber);

    // For field validation purposes
    Boolean existsByEmployeeNumber(String employeeNumber);
    Employee findByEmail(String email);
    Employee findByPhone(String phone);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);

    // For Search Bar
    @Query("select e from Employee e where " +
                    "e.employeeNumber like %:searchVal% or " +
                    "e.name like %:searchVal% or " +
                    "e.surname like %:searchVal%"
    )
    List<Employee> findByNumberOrNameOrSurname(@Param("searchVal") String searchVal);
}
