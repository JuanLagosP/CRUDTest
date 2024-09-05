package com.crudtest.model.dto;

import java.io.Serial;
import java.io.Serializable;

public class SearchInfoDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String employeeNumber;
    private String name;
    private String surname;

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
