package com.example.xml_product_shop.products_shop.services.impl;

import com.example.xml_product_shop.products_shop.entities.products.ExportNamePriceProductDTO;
import com.example.xml_product_shop.products_shop.entities.products.ExportSoldProductsDTO;
import com.example.xml_product_shop.products_shop.entities.users.*;
import com.example.xml_product_shop.products_shop.repositories.UserRepository;
import com.example.xml_product_shop.products_shop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

        this.mapper = new ModelMapper();
    }

    @Override
    @Transactional
    public SellersExportDTO findAllWithSoldProducts() {
        List<User> users = this.userRepository.findAllWithSoldProducts();

        List<UserWithSoldProductsDTO> pDtos =
                users
                        .stream()
                        .map(u -> this.mapper.map(u, UserWithSoldProductsDTO.class))
                        .collect(Collectors.toList());

        return new SellersExportDTO(pDtos);
    }

//    @Override
//    @Transactional
//    public ExportSellersWithCountsDTO findAllWithSoldProductsAndCounts() {
//        List<User> users = this.userRepository.findAllWithSoldProductsOrderByCount();
//
//        List<ExportUserWithSoldCountDTO> dtos =
//                users
//                        .stream()
//                        .map(this::createExportUserWithSoldCountDTO)
//                        .collect(Collectors.toList());
//
//        return new ExportSellersWithCountsDTO(dtos);
//    }
//
//    private ExportUserWithSoldCountDTO createExportUserWithSoldCountDTO(User u) {
//        ExportUserWithSoldCountDTO userDTO = this.mapper.map(u, ExportUserWithSoldCountDTO.class);
//
//        List<ExportNamePriceProductDTO> namePriceProductDTOS = u.getSellingItems()
//                .stream()
//                .map(p -> this.mapper.map(p, ExportNamePriceProductDTO.class))
//                .collect(Collectors.toList());
//
//        ExportSoldProductsDTO soldProductsDTO = new ExportSoldProductsDTO(namePriceProductDTOS);
//        userDTO.setSoldProducts(soldProductsDTO);
//
//        return userDTO;
//    }
}
