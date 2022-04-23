package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.ImportTownDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class TownServiceImpl implements TownService {
    private final Path path =
            Path.of("src", "main", "resources", "files", "json", "towns.json");
    private final TownRepository townRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, Gson gson, Validator validator, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.validator = validator;
        this.modelMapper = modelMapper;
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
    public String importTowns() throws IOException {
        String json = this.readTownsFileContent();

        ImportTownDTO[] importTownDTOs = this.gson.fromJson(json, ImportTownDTO[].class);

        List<String> result = new ArrayList<>();
        for (ImportTownDTO info : importTownDTOs) {
            Set<ConstraintViolation<ImportTownDTO>> validationErrors = this.validator.validate(info);

            if (validationErrors.isEmpty()) {
                Optional<Town> optTown =
                        this.townRepository.findByTownName(info.getTownName());

                if (optTown.isEmpty()) {
                    Town town = this.modelMapper.map(info, Town.class);

                    String msg = String.format("Successfully imported town %s - %d",
                            town.getTownName(), town.getPopulation());

                    result.add(msg);

                    this.townRepository.save(town);
                } else {
                    result.add("Invalid town");
                }
            } else {
                result.add("Invalid town");
            }
        }

        return String.join("\n", result);
    }
}
