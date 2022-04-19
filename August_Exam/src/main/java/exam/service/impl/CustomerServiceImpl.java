package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.json.ImportCustomerDTO;
import exam.model.entities.Customer;
import exam.model.entities.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Path path =
            Path.of("src", "main", "resources", "files", "json", "customers.json");
    private final CustomerRepository customerRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final TownRepository townRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, Gson gson, Validator validator, ModelMapper modelMapper, TownRepository townRepository) {
        this.customerRepository = customerRepository;
        this.gson = gson;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importCustomers() throws IOException {
        String json = this.readCustomersFileContent();

        ImportCustomerDTO[] customerDTOs = this.gson.fromJson(json, ImportCustomerDTO[].class);

        return Arrays.stream(customerDTOs)
                .map(this::importCustomer)
                .collect(Collectors.joining("\n"));
    }

    public String importCustomer(ImportCustomerDTO info){
        Set<ConstraintViolation<ImportCustomerDTO>> validationErrors = this.validator.validate(info);

        if (validationErrors.isEmpty()){
            Optional<Town> town = townRepository.findByName(info.getTown().getName());

            if (town.isPresent()){
                Customer customer = this.modelMapper.map(info, Customer.class);
                customer.setTown(town.get());
                customerRepository.save(customer);

                return "Successfully imported Customer " + customer.getFirstName() + " " + customer.getLastName() + " - " + customer.getEmail();
            }
            else {
                return "Invalid Customer";
            }
        }else{
            return "Invalid Customer";
        }
    }

}
