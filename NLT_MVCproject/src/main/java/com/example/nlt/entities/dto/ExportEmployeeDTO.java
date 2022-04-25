package com.example.nlt.entities.dto;

import com.example.nlt.entities.Employee;

public class ExportEmployeeDTO {
    private String fullName;
    private int age;
    private String projectName;

    public ExportEmployeeDTO(Employee employee) {
        this.fullName = employee.getFirstName() + " " + employee.getLastName();
        this.age = employee.getAge();
        this.projectName = employee.getProject().getName();
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getProjectName() {
        return projectName;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Name: %s", fullName)).append(System.lineSeparator());
        builder.append(String.format("  Age: %d", age)).append(System.lineSeparator());
        builder.append(String.format("  Project name: %s", projectName)).append(System.lineSeparator());
        return builder.toString();
    }
}
