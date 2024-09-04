package com.crudtest.controller;

import com.crudtest.model.dto.EmployeeDto;
import com.crudtest.model.entity.Employee;
import com.crudtest.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<?> findAllEmployees() {
        Map<String, Object> response = new HashMap<>();
        List<Employee> employees;

        try {
            employees = employeeService.findAllEmployees();
        } catch (DataAccessException e) {
            response.put("message", "Error querying the database!");
            response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));

            return ResponseEntity.status(500).body(response);
        }

        if (employees.isEmpty()) {
            response.put("message", "No employees found!");

            return ResponseEntity.status(404).body(response);
        }

        return ResponseEntity.ok().body(employees);
    }

    @GetMapping("/employees/{employeeNumber}")
    public ResponseEntity<?> findByEmployeeNumber(@PathVariable String employeeNumber) {
        Map<String, Object> response = new HashMap<>();
        Employee employee;

        try {
            employee = employeeService.findByEmployeeNumber(employeeNumber);
        } catch (DataAccessException e) {
            response.put("message", "Error querying the database!");
            response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));

            return ResponseEntity.status(500).body(response);
        }

        if (employee == null) {
            response.put("message", "Employee not found!");

            return ResponseEntity.status(404).body(response);
        }

        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("/employees")
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDto employee) {
        Map<String, Object> response = new HashMap<>();
        Employee newEmployee;

        try {
            newEmployee = employeeService.saveEmployee(employee);
        } catch (DuplicateKeyException e) {
            response.put("message", e.getMessage());

            return ResponseEntity.status(400).body(response);
        } catch (DataAccessException e) {
            response.put("message", "Error while persisting the employee to database!");
            response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));

            return ResponseEntity.status(500).body(response);
        }

        response.put("message", "Employee created!");
        response.put("employee", newEmployee);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/employees/{employeeNumber}")
    public ResponseEntity<?> updateEmployee(@PathVariable String employeeNumber, @RequestBody EmployeeDto employee) {
        Map<String, Object> response = new HashMap<>();
        Employee currentEmployee;

        if (!employeeService.existsByEmployeeNumber(employeeNumber)) {
            response.put("message", "Employee not found!");

            return ResponseEntity.status(404).body(response);
        }

        try {
            currentEmployee = employeeService.updateEmployee(employeeNumber, employee);
        } catch (DuplicateKeyException e) {
            response.put("message", e.getMessage());

            return ResponseEntity.status(400).body(response);
        }   catch (DataAccessException e) {
            response.put("message", "Error while updating the employee in database!");
            response.put("error", e.getMessage().concat(e.getCause().getMessage()));

            return ResponseEntity.status(500).body(response);
        }

        response.put("message", "Employee updated!");
        response.put("employee", currentEmployee);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/employees/{employeeNumber}")
    public ResponseEntity<?> deleteByEmployeeNumber(@PathVariable String employeeNumber) {
        Map<String, Object> response = new HashMap<>();

        if (!employeeService.existsByEmployeeNumber(employeeNumber)) {
            response.put("message", "Employee not found!");

            return ResponseEntity.status(404).body(response);
        }

        try {
            employeeService.deleteByEmployeeNumber(employeeNumber);
        } catch (DataAccessException e) {
            response.put("message", "Error while deleting the employee from database!");
            response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));

            return ResponseEntity.status(500).body(response);
        }

        response.put("message", "Employee deleted!");

        return ResponseEntity.ok().body(response);
    }


}
