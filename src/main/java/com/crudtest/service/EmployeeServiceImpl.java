package com.crudtest.service;

import com.crudtest.model.dto.EmployeeDto;
import com.crudtest.model.entity.Employee;
import com.crudtest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Year;
import java.util.Date;
import java.util.Random;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private String generateEmployeeNumber(EmployeeDto employee) {
        Random rnd = new Random();
        String hireYear = employee.getHireDate().toString().substring(30);
        String initials = employee.getSurname().substring(0, 2) + employee.getName().charAt(0);
        String rndNum = String.valueOf(1000 + rnd.nextInt(9000));

        String employeeNumber = "E" + hireYear + initials.toUpperCase() + rndNum;

        while(employeeRepository.existsByEmployeeNumber(employeeNumber)) {
            rndNum = String.valueOf(1000 + rnd.nextInt(9000));
            employeeNumber = "E" + hireYear + initials.toUpperCase() + rndNum;
        }

        return employeeNumber;
    }

    private void setAttributes(Employee employee, EmployeeDto employeeDto) {
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        employee.setBirthDate(employeeDto.getBirthDate());
        employee.setHireDate(employeeDto.getHireDate());
        employee.setActive(true);
    }

    private Boolean isEmailValid(String employeeNumber, String email) {
        Employee employee = employeeRepository.findByEmail(email);

        return employee == null || employee.getEmployeeNumber().equals(employeeNumber)
                && employee.getActive();
    }

    private Boolean isPhoneValid(String employeeNumber, String phone) {
        Employee employee = employeeRepository.findByPhone(phone);

        return employee == null || employee.getEmployeeNumber().equals(employeeNumber)
                && employee.getActive();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Employee> findAllEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findByEmployeeNumber(String employeeNumber) {
        return employeeRepository.findByEmployeeNumber(employeeNumber);
    }

    @Override
    @Transactional
    public Employee saveEmployee(EmployeeDto employee) throws DuplicateKeyException {
        Employee newEmployee = new Employee();

        if (existsByEmail(employee.getEmail())) {
            throw new DuplicateKeyException("Email already in use!");
        }

        if (existsByPhone(employee.getPhone())) {
            throw new DuplicateKeyException("Phone already in use!");
        }

        setAttributes(newEmployee, employee);
        newEmployee.setEmployeeNumber(generateEmployeeNumber(employee));

        return employeeRepository.save(newEmployee);
    }

    @Override
    @Transactional
    public Employee updateEmployee(String employeeNumber, EmployeeDto employee) throws DuplicateKeyException {
        Employee currentEmployee = findByEmployeeNumber(employeeNumber);

        if (!isEmailValid(employeeNumber, employee.getEmail())) {
            throw new DuplicateKeyException("Email already in use!");
        }

        if (!isPhoneValid(employeeNumber, employee.getPhone())) {
            throw new DuplicateKeyException("Phone already in use!");
        }

        setAttributes(currentEmployee, employee);

        return employeeRepository.save(currentEmployee);
    }

    @Override
    @Transactional
    public void deleteByEmployeeNumber(String employeeNumber) {
        Employee currentEmployee = findByEmployeeNumber(employeeNumber);
        currentEmployee.setActive(false);

        employeeRepository.save(currentEmployee);
    }

    @Override
    @Transactional
    public Boolean existsByEmployeeNumber(String employeeNumber) {
        return employeeRepository.existsByEmployeeNumber(employeeNumber);
    }

    @Override
    @Transactional
    public Boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public Boolean existsByPhone(String phone) {
        return employeeRepository.existsByPhone(phone);
    }
}
