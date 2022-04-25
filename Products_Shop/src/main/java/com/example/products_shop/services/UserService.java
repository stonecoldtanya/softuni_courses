package com.example.products_shop.services;

import com.example.products_shop.entities.users.UserThatHasSoldProductsDTO;
import com.example.products_shop.entities.users.UserWithProductCountDTO;

import java.util.List;

public interface UserService {
    List<UserThatHasSoldProductsDTO> getUsersWithSoldProducts();

    UserWithProductCountDTO getUserWithProductsCount();
}
