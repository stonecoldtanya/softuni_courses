package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.ImportAgentDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AgentServiceImpl implements AgentService {
    private final Path path =
            Path.of("src", "main", "resources", "files", "json", "agents.json");
    private final AgentRepository agentRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final TownRepository townRepository;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, Gson gson, Validator validator, ModelMapper modelMapper, TownRepository townRepository) {
        this.agentRepository = agentRepository;
        this.gson = gson;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importAgents() throws IOException {
        String json = this.readAgentsFromFile();

        ImportAgentDTO[] agentsDTOs = this.gson.fromJson(json, ImportAgentDTO[].class);

        return Arrays.stream(agentsDTOs)
                .map(this::importAgent)
                .collect(Collectors.joining("\n"));
    }

    public String importAgent(ImportAgentDTO info) {
        Set<ConstraintViolation<ImportAgentDTO>> errors =
                this.validator.validate(info);

        if (!errors.isEmpty()) {
            return "Invalid agent";
        }
        Optional<Agent> optAgent = this.agentRepository.findByFirstName(info.getFirstName());
        if (optAgent.isPresent()) {
            return "Invalid agent";
        }

        Agent agent = this.modelMapper.map(info, Agent.class);
        Optional<Town> town = this.townRepository.findByTownName(info.getTown());

        agent.setTown(town.get());

        this.agentRepository.save(agent);

        return "Successfully imported agent - " + agent.getFirstName() + " " + agent.getLastName();
    }

}
