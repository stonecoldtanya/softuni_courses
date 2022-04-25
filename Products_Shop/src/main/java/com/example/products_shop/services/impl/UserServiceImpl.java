package com.example.products_shop.services.impl;

import com.example.products_shop.entities.users.User;
import com.example.products_shop.entities.users.UserThatHasSoldProductsDTO;
import com.example.products_shop.entities.users.UserWithProductCountDTO;
import com.example.products_shop.entities.users.UserWithProductsSoldDTO;
import com.example.products_shop.repositories.UserRepository;
import com.example.products_shop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public List<UserThatHasSoldProductsDTO> getUsersWithSoldProducts() {
        List<User> allWithSoldProducts = this.userRepository.findAllWithSoldProducts();

        return allWithSoldProducts
                .stream()
                .map(user -> this.mapper.map(user, UserThatHasSoldProductsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserWithProductCountDTO getUserWithProductsCount(){
        List<User> userWithProductsSold = this.userRepository.findAllWithSoldProductsOrderByCount();

        List<UserWithProductsSoldDTO> userWithProductsSoldDTOS = userWithProductsSold.stream()
                .map(user -> this.mapper.map(user, UserWithProductsSoldDTO.class))
                .collect(Collectors.toList());

        return new UserWithProductCountDTO(userWithProductsSoldDTOS.size(), new HashSet<>(userWithProductsSoldDTOS));
    }

}
