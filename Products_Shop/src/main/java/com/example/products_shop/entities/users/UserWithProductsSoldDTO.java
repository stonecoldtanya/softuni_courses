package com.example.products_shop.entities.users;

import com.example.products_shop.entities.products.ProductTotalPriceDTO;

import java.util.Set;

public class UserWithProductsSoldDTO {
    private String firstName;
    private String lastName;
    private int age;
    private Set<ProductTotalPriceDTO> soldProducts;

    public UserWithProductsSoldDTO() {

    }
    public UserWithProductsSoldDTO(String firstName, String lastName, int age, Set<ProductTotalPriceDTO> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = soldProducts;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public Set<ProductTotalPriceDTO> getSoldProducts() {
        return soldProducts;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSoldProducts(Set<ProductTotalPriceDTO> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
