package com.example.xml_product_shop.products_shop;

import com.example.xml_product_shop.products_shop.entities.categories.ExportCategoryContentDTO;
import com.example.xml_product_shop.products_shop.entities.categories.ExportCategoryDTO;
import com.example.xml_product_shop.products_shop.entities.products.ProductsInRangeExportDTO;
import com.example.xml_product_shop.products_shop.entities.users.SellersExportDTO;
import com.example.xml_product_shop.products_shop.services.ProductService;
import com.example.xml_product_shop.products_shop.services.SeedService;
import com.example.xml_product_shop.products_shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public Runner(SeedService seedService, ProductService productService, UserService userService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;

    }

    @Override
    public void run(String... args) throws Exception{
//        this.seedService.seedAll();
//        productsInRange();
//        this.findUserWithSoldProducts();
//        this.findAllCategoriesByProductCount();
//        Query4();

    }

    private void findAllCategoriesByProductCount() throws JAXBException{
        ExportCategoryDTO categoryStats = this.productService.getCategoryStatistics();

        JAXBContext context = JAXBContext.newInstance(ExportCategoryDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(categoryStats, System.out);

    }

    private void productsInRange() throws JAXBException {
        ProductsInRangeExportDTO productsInRange = this.productService.getInRange(500,1000);

        JAXBContext context = JAXBContext.newInstance(ProductsInRangeExportDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(productsInRange, System.out);
    }

    private void findUserWithSoldProducts() throws JAXBException {
        SellersExportDTO result = this.userService.findAllWithSoldProducts();

        JAXBContext context = JAXBContext.newInstance(SellersExportDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(result, System.out);

    }


}
