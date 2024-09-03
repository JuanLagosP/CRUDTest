package com.crudtest.service;

import com.crudtest.model.dto.EmployeeDto;
import com.crudtest.model.entity.Employee;
import com.crudtest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private String generateEmployeeNumber() {
        String currentYear = Year.now().toString();

        return "E" + currentYear + String.format("%04d", employeeRepository.count() + 1);
    }

    private void setAttributes(Employee employee, EmployeeDto employeeDto) {
        employee.setEmployeeNumber(generateEmployeeNumber());
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        employee.setBirthDate(employeeDto.getBirthDate());
        employee.setHireDate(employeeDto.getHireDate());
    }

    private Boolean isEmailValid(String employeeNumber, String email) {
        Employee employee = employeeRepository.findByEmail(email);

        return employee == null || employee.getEmployeeNumber().equals(employeeNumber);
    }

    private Boolean isPhoneValid(String employeeNumber, String phone) {
        Employee employee = employeeRepository.findByPhone(phone);

        return employee == null || employee.getEmployeeNumber().equals(employeeNumber);
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
    public Employee saveEmployee(EmployeeDto employee) throws DuplicateKeyException {
        Employee newEmployee = new Employee();

        if (existsByEmail(employee.getEmail())) {
            throw new DuplicateKeyException("Email already in use!");
        }

        if (existsByPhone(employee.getPhone())) {
            throw new DuplicateKeyException("Phone already in use!");
        }

        setAttributes(newEmployee, employee);

        return employeeRepository.save(newEmployee);
    }

    @Override
    public Employee updateEmployee(String employeeNumber, EmployeeDto employee) throws DuplicateKeyException {
        Employee currentEmployee = findByEmployeeNumber(employeeNumber);

        if (!isEmailValid(employeeNumber, employee.getEmail())) {
            throw new DuplicateKeyException("Email already in use");
        }

        if (!isPhoneValid(employeeNumber, employee.getPhone())) {
            throw new DuplicateKeyException("Phone already in use");
        }

        setAttributes(currentEmployee, employee);

        return employeeRepository.save(currentEmployee);
    }

    @Override
    public void deleteByEmployeeNumber(String employeeNumber) {
        employeeRepository.deleteByEmployeeNumber(employeeNumber);
    }

    @Override
    public Boolean existsByEmployeeNumber(String employeeNumber) {
        return employeeRepository.existsByEmployeeNumber(employeeNumber);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByPhone(String phone) {
        return employeeRepository.existsByPhone(phone);
    }
}
