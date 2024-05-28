package com.demo.product.repositories;

import com.demo.product.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByProductCode(String productCode);

    Optional<Product> findByName(String name);

    Page<Product> findByCategory(String category, Pageable pageable);

    Page<Product> findByBrand(String brand, Pageable pageable);

    @Query("{ 'category' : ?0, 'brand' : ?1 }")
    List<Product> findByCategoryAndBrand(String category, String brand);
}