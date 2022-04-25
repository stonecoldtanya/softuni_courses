package com.example.nlt.services;

import com.example.nlt.entities.Company;
import com.example.nlt.entities.dto.ImportCompaniesRootDTO;
import com.example.nlt.entities.dto.ImportCompanyDTO;
import com.example.nlt.repositories.CompanyRepository;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private final Path path = Path.of("src/main/resources/files/xmls/companies.xml");

    private final CompanyRepository companyRepository;
    private final Validator validator;
    private final ModelMapper mapper;
    private final Unmarshaller unmarshaller;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, Validator validator, ModelMapper mapper) throws JAXBException {
        this.companyRepository = companyRepository;
        this.validator = validator;
        this.mapper = mapper;

        JAXBContext context = JAXBContext.newInstance(ImportCompaniesRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();
    }

    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    public String readCompaniesFromFile() throws IOException {
        return Files.readString(path);
    }

    public String importCompanies() throws JAXBException, FileNotFoundException {
        ImportCompaniesRootDTO companiesDTOs = (ImportCompaniesRootDTO) this.unmarshaller.unmarshal(
                new FileReader(path.toAbsolutePath().toString()));


        return companiesDTOs
                .getCompanies()
                .stream()
                .map(this::importCompany)
                .collect(Collectors.joining("\n"));
    }

    public String importCompany(ImportCompanyDTO info) {
        Set<ConstraintViolation<ImportCompanyDTO>> errors =
                this.validator.validate(info);

        if (!errors.isEmpty()) {
            return "Invalid company";
        }
        Optional<Company> optCompany = this.companyRepository.findByName(info.getName());

        if (optCompany.isPresent()) {
            return "Invalid Company";
        }

        Company company = this.mapper.map(info, Company.class);
        this.companyRepository.save(company);

        return "Created Company - " + info.getName();

    }
}
