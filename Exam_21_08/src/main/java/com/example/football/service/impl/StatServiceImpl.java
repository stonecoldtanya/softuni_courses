package com.example.football.service.impl;


import com.example.football.models.dto.json.ImportTeamDTO;
import com.example.football.models.dto.xml.ImportStatDTO;
import com.example.football.models.dto.xml.ImportStatInfoDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
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
public class StatServiceImpl implements StatService {
    private final Path path =
            Path.of("src", "main", "resources", "files", "xml", "stats.xml");
    private final StatRepository statRepository;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final Unmarshaller unmarshaller;

    @Autowired
    public StatServiceImpl(StatRepository statRepository, Validator validator, ModelMapper modelMapper) throws JAXBException {
        this.statRepository = statRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;

        JAXBContext context = JAXBContext.newInstance(ImportStatDTO.class);
        this.unmarshaller = context.createUnmarshaller();
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importStats() throws FileNotFoundException, JAXBException {
        ImportStatDTO statDTOs = (ImportStatDTO) this.unmarshaller.unmarshal(
                new FileReader(path.toAbsolutePath().toString()));

        return statDTOs
                .getStats()
                .stream()
                .map(this::importStat)
                .collect(Collectors.joining("\n"));
    }
    private  String importStat(ImportStatInfoDTO statsInfo) {

        Set<ConstraintViolation<ImportStatInfoDTO>> errors = validator.validate(statsInfo);

        if (errors.isEmpty()) {
            Optional<Stat> optStat = statRepository.findByShootingAndPassingAndEndurance(statsInfo.getShooting(),statsInfo.getPassing(),statsInfo.getEndurance());

            if (optStat.isEmpty()) {
                Stat stat = modelMapper.map(statsInfo,Stat.class);

                statRepository.save(stat);

                return String.format("Successfully imported Stat %.2f - %.2f - %.2f",stat.getShooting(),stat.getPassing(),stat.getEndurance());
            }
            else {
                return "Invalid Stats";
            }
        }
        else {
            return "Invalid Stats";
        }

    }
}