package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.json.ImportCustomerDTO;
import exam.model.dto.json.ImportLaptopDTO;
import exam.model.dto.json.LaptopExportDto;
import exam.model.entities.Customer;
import exam.model.entities.Laptop;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LaptopServiceImpl implements LaptopService {
    private final Path path =
            Path.of("src", "main", "resources", "files", "json", "laptops.json");
    private final LaptopRepository laptopRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final ShopRepository shopRepository;
    public LaptopServiceImpl(LaptopRepository laptopRepository, Gson gson, Validator validator, ModelMapper modelMapper, ShopRepository shopRepository) {
        this.laptopRepository = laptopRepository;
        this.gson = gson;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.shopRepository = shopRepository;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importLaptops() throws IOException {
        String json = this.readLaptopsFileContent();

        ImportLaptopDTO[] laptopDTOs = this.gson.fromJson(json, ImportLaptopDTO[].class);

        return Arrays.stream(laptopDTOs)
                .map(this::importLaptop)
                .collect(Collectors.joining("\n"));
    }

    public String importLaptop(ImportLaptopDTO info){
        Set<ConstraintViolation<ImportLaptopDTO>> validationErrors = this.validator.validate(info);

        if (validationErrors.isEmpty()){
            Optional<Shop> shop = shopRepository.findByName(info.getShop().getName());
            Optional<Laptop> checked = laptopRepository.findByMacAddress(info.getMacAddress());
            if (shop.isPresent() && checked.isEmpty()) {
                Laptop laptop = this.modelMapper.map(info, Laptop.class);
                laptop.setShop(shop.get());
                laptopRepository.save(laptop);

                return String.format("Successfully imported Laptop %s - %.2f - %d - %d",laptop.getMacAddress(), laptop.getCpuSpeed(), laptop.getRam(), laptop.getStorage());
            }
            else {
                return "Invalid Laptop";
            }
        }else{
            return "Invalid Laptop";
        }
    }

    @Override
    public String exportBestLaptops() {
        return laptopRepository.findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc()
                .stream()
                .map(l -> this.modelMapper.map(l, LaptopExportDto.class))
                .map(LaptopExportDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
