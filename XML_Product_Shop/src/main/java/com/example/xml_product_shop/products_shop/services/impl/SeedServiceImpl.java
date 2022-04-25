package com.example.xml_product_shop.products_shop.services.impl;

import com.example.xml_product_shop.products_shop.entities.categories.Category;
import com.example.xml_product_shop.products_shop.entities.categories.CategoryImportDTO;
import com.example.xml_product_shop.products_shop.entities.products.Product;
import com.example.xml_product_shop.products_shop.entities.products.ProductImportDTO;
import com.example.xml_product_shop.products_shop.entities.users.User;
import com.example.xml_product_shop.products_shop.entities.users.UserImportDTO;
import com.example.xml_product_shop.products_shop.repositories.CategoryRepository;
import com.example.xml_product_shop.products_shop.repositories.ProductRepository;
import com.example.xml_product_shop.products_shop.repositories.UserRepository;
import com.example.xml_product_shop.products_shop.services.SeedService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {
    private static final Path USERS_XML_PATH =
            Path.of("src","main","resources", "product_shop","users.xml");
    private static final Path CATEGORIES_XML_PATH =
            Path.of("src","main","resources","product_shop","categories.xml");
    private static final Path PRODUCTS_XML_PATH =
            Path.of("src","main","resources","product_shop","products.xml");

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository,
                           CategoryRepository categoryRepository,
                           ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;

        this.mapper = new ModelMapper();
    }

    @Override
    public void seedUsers() throws FileNotFoundException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(UserImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        FileReader xmlReader = new FileReader(USERS_XML_PATH.toAbsolutePath().toString());
        UserImportDTO importedUsers = (UserImportDTO) unmarshaller.unmarshal(xmlReader);

        List<User> users = importedUsers.getUsers()
                .stream()
                .map(u-> this.mapper.map(u, User.class))
                .collect(Collectors.toList());

        this.userRepository.saveAll(users);
    }


    @Override
    public void seedCategories() throws FileNotFoundException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(CategoryImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        FileReader xmlReader = new FileReader(CATEGORIES_XML_PATH.toAbsolutePath().toString());
        CategoryImportDTO importedCategories = (CategoryImportDTO) unmarshaller.unmarshal(xmlReader);

        List<Category> categories = importedCategories
                .getCategories()
                .stream()
                .map(c -> new Category(c.getName()))
                .collect(Collectors.toList());

        this.categoryRepository.saveAll(categories);

    }

    @Override
    public void seedProducts() throws FileNotFoundException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(ProductImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        FileReader xmlReader = new FileReader(PRODUCTS_XML_PATH.toAbsolutePath().toString());
        ProductImportDTO importedProducts = (ProductImportDTO) unmarshaller.unmarshal(xmlReader);

        List<Product> products = importedProducts
                .getProducts()
                .stream()
                .map(p -> new Product(p.getName(), p.getPrice()))
                .map(this::setRandomCategories)
                .map(this::setRandomSeller)
                .map(this::setRandomBuyer)
                .collect(Collectors.toList());

        this.productRepository.saveAll(products);
    }

    private Product setRandomCategories(Product product) {
        Random random = new Random();
        long categoriesDbCount = this.categoryRepository.count();

        int count = random.nextInt((int) categoriesDbCount);

        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < count; i++) {
            int randomId = random.nextInt((int) categoriesDbCount) + 1;

            Optional<Category> randomCategory = this.categoryRepository.findById(randomId);

            categories.add(randomCategory.get());
        }

        product.setCategories(categories);
        return product;
    }

    private Product setRandomBuyer(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(944)) > 0) {
            return product;
        }

        Optional<User> buyer = getRandomUser();

        product.setBuyer(buyer.get());

        return product;
    }

    private Product setRandomSeller(Product product) {
        Optional<User> seller = getRandomUser();

        product.setSeller(seller.get());

        return product;
    }

    private Optional<User> getRandomUser() {
        long counter = this.userRepository.count();

        int randomUserId = new Random().nextInt((int) counter) + 1;

        return   this.userRepository.findById(randomUserId);
    }

}
