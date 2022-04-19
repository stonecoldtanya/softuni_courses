package exam.service.impl;

import exam.model.dto.xml.ImportShopDTO;
import exam.model.dto.xml.ImportShopRootDTO;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import org.modelmapper.ModelMapper;
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
public class ShopServiceImpl implements ShopService {
    private final Path path =
            Path.of("src", "main", "resources", "files", "xml", "shops.xml");
    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository, Validator validator, ModelMapper modelMapper) throws JAXBException {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;

        JAXBContext context = JAXBContext.newInstance(ImportShopRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();

    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        ImportShopRootDTO shopDTOs = (ImportShopRootDTO) this.unmarshaller.unmarshal(
                new FileReader(path.toAbsolutePath().toString()));

        return shopDTOs
                .getShops()
                .stream()
                .map(this::importShop)
                .collect(Collectors.joining("\n"));
    }
    public String importShop(ImportShopDTO dto) {
        Set<ConstraintViolation<ImportShopDTO>> errors = this.validator.validate(dto);

        if (errors.isEmpty()) {
            Optional<Shop> optShop = this.shopRepository.findByName(dto.getName());
            Optional<Town> town = townRepository.findByName(dto.getTown().getName());
            if (town.isPresent() && optShop.isEmpty()) {
                Shop shop = this.modelMapper.map(dto, Shop.class);

                shop.setTown(town.get());
                this.shopRepository.save(shop);

                return "Successfully imported shop" + shop.getName() + " - " + shop.getIncome();
            }else {
                return "Invalid Shop";
            }
        } else {
            return "Invalid Shop";
        }
    }
}
