package com.example.products_shop.repositories;

import com.example.products_shop.entities.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Integer> {
}
