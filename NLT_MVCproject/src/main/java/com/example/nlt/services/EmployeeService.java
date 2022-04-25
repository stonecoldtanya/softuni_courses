package com.example.nlt.services;

import com.example.nlt.entities.dto.ExportEmployeeDTO;
import com.example.nlt.entities.Employee;
import com.example.nlt.entities.Project;
import com.example.nlt.entities.dto.ImportEmployeeDTO;
import com.example.nlt.entities.dto.ImportEmployeesRootDTO;
import com.example.nlt.repositories.EmployeeRepository;
import com.example.nlt.repositories.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Path path = Path.of("src/main/resources/files/xmls/employees.xml");

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final Validator validator;
    private final ModelMapper mapper;
    private final Unmarshaller unmarshaller;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ProjectRepository projectRepository, Validator validator, ModelMapper mapper) throws JAXBException {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.validator = validator;
        this.mapper = mapper;

        JAXBContext context = JAXBContext.newInstance(ImportEmployeesRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();
    }


    public boolean areImported() {
        return this.employeeRepository.count() > 0;
    }

    public String readEmployeesFromFile() throws IOException {
        return Files.readString(path);
    }

    public String importEmployees() throws JAXBException, FileNotFoundException {
        ImportEmployeesRootDTO rootDTO = (ImportEmployeesRootDTO) this.unmarshaller.unmarshal(
                new File(path.toAbsolutePath().toString()));

        return rootDTO
                .getEmployees()
                .stream()
                .map(this::importEmployee)
                .collect(Collectors.joining("\n"));
    }
    private String importEmployee(ImportEmployeeDTO info) {

        Set<ConstraintViolation<ImportEmployeeDTO>> errors =
                this.validator.validate(info);

        if (!errors.isEmpty()) {
            return "Invalid Employee or Project";
        }

        Employee employee = this.mapper.map(info, Employee.class);

        Optional<Project> optProject =
                this.projectRepository.findByName(info.getProject().getName());

        if (optProject.isEmpty()) {
            return "Invalid Project Name";
        }

        employee.setProject(optProject.get());

        this.employeeRepository.save(employee);

        return "Import Employee - " + employee.getFirstName() +
                " " + employee.getLastName();
    }


    public List<ExportEmployeeDTO> getEmployeesAbove() {
        List<Employee> employees = this.employeeRepository
                .findByAgeGreaterThanOrderByProjectNameAsc(25);

        return employees
                .stream()
                .map(ExportEmployeeDTO::new)
                .collect(Collectors.toList());

    }
}
