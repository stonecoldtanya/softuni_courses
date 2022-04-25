package com.example.products_shop;

import com.example.products_shop.entities.categories.CategoryStats;
import com.example.products_shop.entities.products.ProductWithMissingBuyerDTO;
import com.example.products_shop.entities.users.UserThatHasSoldProductsDTO;
import com.example.products_shop.services.ProductService;
import com.example.products_shop.services.SeedService;
import com.example.products_shop.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private final Gson gson;

    @Autowired
    public Runner(SeedService seedService, ProductService productService, UserService userService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;

        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void run(String... args) throws Exception{
//        this.seedService.seedUsers();
//        this.seedService.seedCategories();
//        this.seedService.seedProducts();
//        Task1_Query1();
//        Task2_Query2();
//        Task3_Query3();
        Task4_Query4();

    }
    private void Task1_Query1() {
        List<ProductWithMissingBuyerDTO> productsForSell = this.productService.getProductsInPriceRangeForSell(500, 1000);

        String task1 = this.gson.toJson(productsForSell);

        System.out.println(task1);
    }
    private void Task2_Query2() {
        List<UserThatHasSoldProductsDTO> usersWithSoldProducts = this.userService.getUsersWithSoldProducts();

        String task2 = this.gson.toJson(usersWithSoldProducts);

        System.out.println(task2);
    }

    private void Task3_Query3() {
        List<CategoryStats> result = this.productService.getCategoryStatistics();

        String task3 = this.gson.toJson(result);
        System.out.println(task3);
    }
    private void Task4_Query4() {
        String task4 = gson.toJson(userService.getUserWithProductsCount());
        System.out.println(task4);
    }

}
