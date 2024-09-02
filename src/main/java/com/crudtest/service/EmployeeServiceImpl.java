package com.crudtest.service;

import com.crudtest.model.dto.EmployeeDto;
import com.crudtest.model.entity.Employee;
import com.crudtest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findByEmployeeNumber(String employeeNumber) {
        return employeeRepository.findByEmployeeNumber(employeeNumber);
    }

    @Override
    public Employee saveEmployee(EmployeeDto employee) {
        Employee newEmployee = new Employee();
        setAttributes(newEmployee, employee);

        return employeeRepository.save(newEmployee);
    }

    @Override
    public Employee updateEmployee(String employeeNumber, EmployeeDto employee) {
        Employee currentEmployee = findByEmployeeNumber(employeeNumber);
        setAttributes(currentEmployee, employee);

        return currentEmployee;
    }

    @Override
    public void deleteByEmployeeNumber(String employeeNumber) {
        employeeRepository.deleteByEmployeeNumber(employeeNumber);
    }

    private void setAttributes(Employee employee, EmployeeDto employeeDto) {
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        employee.setBirthDate(employeeDto.getBirthDate());
        employee.setHireDate(employeeDto.getHireDate());
    }
}
