package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.OfferListDTO;
import softuni.exam.models.dto.xml.ImportOfferDTO;
import softuni.exam.models.dto.xml.ImportOfferRootDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private final Path path =
            Path.of("src", "main", "resources", "files", "xml", "offers.xml");
    private final OfferRepository offerRepository;
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, Validator validator, ModelMapper modelMapper, AgentRepository agentRepository, ApartmentRepository apartmentRepository) throws JAXBException {
        this.offerRepository = offerRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
        JAXBContext context = JAXBContext.newInstance(ImportOfferRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        ImportOfferRootDTO offersDTOs = (ImportOfferRootDTO) this.unmarshaller.unmarshal(
                new FileReader(path.toAbsolutePath().toString()));

        return offersDTOs
                .getOffers()
                .stream()
                .map(this::importOffer)
                .collect(Collectors.joining("\n"));
    }

    public String importOffer(ImportOfferDTO info) {
        Set<ConstraintViolation<ImportOfferDTO>> errors =
                this.validator.validate(info);

        if (!errors.isEmpty()) {
            return "Invalid offer";
        }

        Optional<Agent> agent = this.agentRepository.findByFirstName(info.getAgent().getFirstName());
        Optional<Apartment> apartment = this.apartmentRepository.findById(info.getApartment().getId());

        Offer offer = this.modelMapper.map(info, Offer.class);
        if (agent.isPresent() && apartment.isPresent()) {
            offer.setAgent(agent.get());
            offer.setApartment(apartment.get());

            this.offerRepository.save(offer);

            return String.format("Successfully imported offer %.2f", offer.getPrice());
        } else {
            return "Invalid offer";
        }

    }

    @Override
    public String exportOffers() {
        return offerRepository.findAllByApartmentApartmentTypeOrderByApartmentAreaDescPriceAsc(ApartmentType.three_rooms)
                .stream()
                .map(offer -> modelMapper.map(offer, OfferListDTO.class))
                .map(OfferListDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));

    }
}
