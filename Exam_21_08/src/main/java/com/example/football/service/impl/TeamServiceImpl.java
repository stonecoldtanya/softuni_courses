package com.example.football.service.impl;

import com.example.football.models.dto.json.ImportTeamDTO;
import com.example.football.models.dto.json.ImportTownDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private TownRepository townRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, Gson gson, ModelMapper modelMapper, Validator validator, TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.gson = gson;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.townRepository = townRepository;
    }


    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "json", "teams.json");

        return Files.readString(path);
    }

    @Override
    public String importTeams() throws IOException {
        String json = this.readTeamsFileContent();
        ImportTeamDTO[] teams = this.gson.fromJson(json, ImportTeamDTO[].class);

        List<String> result = new ArrayList<>();
        for (ImportTeamDTO info : teams) {
            Set<ConstraintViolation<ImportTeamDTO>> validationErrors = this.validator.validate(info);
            if (validationErrors.isEmpty()) {

                Optional<Team> optTeam = this.teamRepository.findByName(info.getName());
                Optional<Town> town = this.townRepository.findByName(info.getTownName());

                if (optTeam.isEmpty() && town.isPresent()) {
                    Team team = this.modelMapper.map(info, Team.class);
                    team.setTown(town.get());
                    String teamImported = String.format("Successfully imported Team %s - %d", team.getName(), team.getFanBase());
                    result.add(teamImported);

                    teamRepository.save(team);
                } else {
                    result.add("Invalid Team");
                }
            }else{
                result.add("Invalid Team");
            }
        }

        return String.join("\n", result);
        }
}
