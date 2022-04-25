package com.example.football.service.impl;

import com.example.football.models.dto.xml.ImportPlayerDTO;
import com.example.football.models.dto.xml.ImportPlayerInfoDTO;
import com.example.football.models.dto.xml.ImportStatDTO;
import com.example.football.models.dto.xml.ImportStatInfoDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PlayerServiceImpl implements PlayerService {
    private final Path path =
            Path.of("src", "main", "resources", "files", "xml", "players.xml");
    private final PlayerRepository playerRepository;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final Unmarshaller unmarshaller;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, Validator validator, ModelMapper modelMapper, StatRepository statRepository,TeamRepository teamRepository, TownRepository townRepository ) throws JAXBException {
        this.playerRepository = playerRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;

        JAXBContext context = JAXBContext.newInstance(ImportPlayerDTO.class);
        this.unmarshaller = context.createUnmarshaller();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importPlayers() throws FileNotFoundException, JAXBException{
        ImportPlayerDTO playerDTOs = (ImportPlayerDTO) this.unmarshaller.unmarshal(
                new FileReader(path.toAbsolutePath().toString()));

        return playerDTOs
                .getPlayers()
                .stream()
                .map(this::importPlayers)
                .collect(Collectors.joining("\n"));

    }
    private  String importPlayers(ImportPlayerInfoDTO playerInfo) {
        Set<ConstraintViolation<ImportPlayerInfoDTO>> errors = validator.validate(playerInfo);

        if (errors.isEmpty()) {
            Optional<Player> optPlayer = playerRepository.findByEmail(playerInfo.getEmail());
            Optional<Team> team = teamRepository.findByName(playerInfo.getTeam().getName());
            Optional<Town> town = townRepository.findByName(playerInfo.getTown().getName());
            Optional<Stat> stats = statRepository.findById(playerInfo.getStat().getId());

            if (team.isPresent() && town.isPresent() && stats.isPresent() && optPlayer.isEmpty()) {
                Player player = this.modelMapper.map(playerInfo, Player.class);

                player.setTeam(team.get());
                player.setTown(town.get());
                player.setStat(stats.get());

                this.playerRepository.save(player);

                return "Successfully imported Player " + player.getFirstName() + " " +
                        player.getLastName() + " - " + player.getPosition().toString();
            }
            else {
                return "Invalid Player";
            }
        }
        else {
            return "Invalid Player";
        }
    }

    @Override
    public String exportBestPlayers() {
        LocalDate before = LocalDate.of(2003, 1,1);
        LocalDate after = LocalDate.of(1995, 1, 1);

        List<Player> players = this.playerRepository.findByBirthDateBetweenOrderByStatShootingDescStatPassingDescStatEnduranceDescLastNameAsc(after, before);
        return players
                .stream()
                .map(Player::toString)
                .collect(Collectors.joining("\n"));
    }
}
