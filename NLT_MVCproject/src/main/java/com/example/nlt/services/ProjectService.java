package com.example.nlt.services;

import com.example.nlt.entities.Company;
import com.example.nlt.entities.Project;
import com.example.nlt.entities.dto.*;
import com.example.nlt.repositories.CompanyRepository;
import com.example.nlt.repositories.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final Path path = Path.of("src/main/resources/files/xmls/projects.xml");

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final Validator validator;
    private final ModelMapper mapper;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, CompanyRepository companyRepository, Validator validator, ModelMapper mapper) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.validator = validator;
        this.mapper = mapper;
    }

    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    public String readProjectsFromFile() throws IOException {
        return Files.readString(path);
    }

    public String importProjects() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(ImportProjectsRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportProjectsRootDTO rootDTO = (ImportProjectsRootDTO)
                unmarshaller.unmarshal(new FileReader(path.toAbsolutePath().toString()));

        StringBuilder sb = new StringBuilder();

        List<ImportProjectDTO> projects = rootDTO.getProjects();
        for (ImportProjectDTO dto : projects) {
            Set<ConstraintViolation<ImportProjectDTO>> errors =
                    this.validator.validate(dto);
            if (!errors.isEmpty()) {
                sb.append("Invalid Project\n");
                continue;
            }

            Project project =
                    this.mapper.map(dto, Project.class);

            Optional<Company> byName =
                    this.companyRepository.findByName(dto.getCompany().getName());

            project.setCompany(byName.get());

            this.projectRepository.save(project);

            sb.append("Created Project - ")
                    .append(project.getName())
                    .append(" for company ")
                    .append(project.getCompany().getName())
                    .append("\n");
        }

        return sb.toString();
    }


    public String getFinishedProjects() {
        List<Project> projects = this.projectRepository.findByIsFinishedTrueOrderByPaymentDesc();

        return projects
                .stream()
                .map(Project::toString)
                .collect(Collectors.joining("\n"));
    }

}
