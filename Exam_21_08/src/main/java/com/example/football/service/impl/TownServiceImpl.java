package com.example.football.service.impl;

import com.example.football.models.dto.json.ImportTownDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.modelmapper.ModelMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TownServiceImpl implements TownService {
    private  final TownRepository townRepository;
    private  final Gson gson;
    private  final Validator validator;
    private  final ModelMapper modelMapper;

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
        Path path = Path.of("src", "main", "resources", "files", "json", "towns.json");

        return Files.readString(path);
    }

    @Override
    public String importTowns() throws IOException {
        String json = this.readTownsFileContent();

        ImportTownDTO[] importTownDTOs = this.gson.fromJson(json, ImportTownDTO[].class);

        List<String> result = new ArrayList<>();
        for (ImportTownDTO info : importTownDTOs) {
            Set<ConstraintViolation<ImportTownDTO>> validationErrors = this.validator.validate(info);

            if (validationErrors.isEmpty()){
                Optional<Town> optTown = this.townRepository.findByName(info.getName());

                if (optTown.isEmpty()){
                    Town town = this.modelMapper.map(info, Town.class);

                    String townImported = String.format("Successfully imported Town %s - %d", town.getName(), town.getPopulation());
                    result.add(townImported);

                    townRepository.save(town);
                }
                else {
                    result.add("Invalid Town");
                }
            }else{
                result.add("Invalid Town");
            }
        }
        return String.join("\n", result);
    }
}
