package com.example.products_shop.repositories;

import com.example.products_shop.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("""
            SELECT u FROM User u
            JOIN u.soldItems p
            WHERE p.buyer IS NOT NULL
            """)
    List<User> findAllWithSoldProducts();
    @Query("""
            SELECT u FROM User u
            JOIN u.soldItems p
            WHERE p.size > 0
            ORDER BY  size(u.soldItems) DESC, u.firstName ASC 
            """)
    List<User> findAllWithSoldProductsOrderByCount();

}
