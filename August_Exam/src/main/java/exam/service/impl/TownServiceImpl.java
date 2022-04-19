package exam.service.impl;

import exam.model.dto.xml.ImportTownDTO;
import exam.model.dto.xml.ImportTownRootDTO;
import exam.model.entities.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.validation.Validator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TownServiceImpl implements TownService {
    private final Path path =
            Path.of("src", "main", "resources", "files", "xml", "towns.xml");
    private final TownRepository townRepository;
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;


    @Autowired
    public TownServiceImpl(TownRepository townRepository, Validator validator, ModelMapper modelMapper ) throws JAXBException  {
        this.townRepository = townRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
        JAXBContext context = JAXBContext.newInstance(ImportTownRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        ImportTownRootDTO playerDTOs = (ImportTownRootDTO) this.unmarshaller.unmarshal(
                new FileReader(path.toAbsolutePath().toString()));

        return playerDTOs
                .getTowns()
                .stream()
                .map(this::importTown)
                .collect(Collectors.joining("\n"));
    }

    public String importTown(ImportTownDTO dto){
        Set<ConstraintViolation<ImportTownDTO>> errors =
                this.validator.validate(dto);
        if (errors.isEmpty()){
            Optional<Town> optTown =
                    this.townRepository
                            .findByName(dto.getName());

            if (optTown.isPresent()) {
                return "Invalid Town";
            }

            Town town = this.modelMapper.map(dto, Town.class);

            this.townRepository.save(town);
            return "Successfully imported Town " + town.getName();
        }else {
            return "Invalid Town";
        }

}
}
