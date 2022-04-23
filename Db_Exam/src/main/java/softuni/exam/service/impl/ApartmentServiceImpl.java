package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.ImportApartmentDTO;
import softuni.exam.models.dto.xml.ImportApartmentRootDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private final Path path =
            Path.of("src", "main", "resources", "files", "xml", "apartments.xml");
    private final ApartmentRepository apartmentRepository;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final Unmarshaller unmarshaller;
    private final TownRepository townRepository;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, Validator validator, ModelMapper modelMapper, TownRepository townRepository) throws JAXBException{
        this.apartmentRepository = apartmentRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.townRepository = townRepository;
        JAXBContext context = JAXBContext.newInstance(ImportApartmentRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();
    }

    @Override
    public boolean areImported() {
        return apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        ImportApartmentRootDTO apartmentsDTOs = (ImportApartmentRootDTO) this.unmarshaller.unmarshal(
                new FileReader(path.toAbsolutePath().toString()));

        return apartmentsDTOs
                .getApartments()
                .stream()
                .map(this::importApartment)
                .collect(Collectors.joining("\n"));
    }

    public String importApartment(ImportApartmentDTO info){
        Set<ConstraintViolation<ImportApartmentDTO>> errors =
                this.validator.validate(info);

        if (!errors.isEmpty()) {
            return "Invalid apartment";
        }

        Optional<Apartment> optApartment =
                this.apartmentRepository.findByArea(info.getArea());
        Optional<Town> town = this.townRepository.findByTownName(info.getTown());
        if(optApartment.isPresent() && town.isPresent()){
            return "Invalid apartment";
        }

        Apartment apartment = this.modelMapper.map(info, Apartment.class);

        apartment.setTown(town.get());
        this.apartmentRepository.save(apartment);
        return String.format("Successfully imported apartment %s - %.2f", apartment.getApartmentType(), apartment.getArea());
    }
}
