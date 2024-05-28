package com.demo.product.repositories;

import com.demo.product.entities.Product;
import com.demo.product.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @MockBean
    private ProductService productService; // Mock the ProductService

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        Product product1 = new Product();
        product1.setProductCode("PRODUCT_001");
        product1.setName("Product 1");
        product1.setCategory("CATEGORY_001");
        product1.setBrand("BRAND_001");
        product1.setPrice(10.00);

        Product product2 = new Product();
        product2.setProductCode("PRODUCT_002");
        product2.setName("Product 2");
        product2.setCategory("CATEGORY_002");
        product2.setBrand("BRAND_002");
        product2.setPrice(20.00);

        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    void testFindByProductCode() {
        Optional<Product> product = productRepository.findByProductCode("PRODUCT_001");
        assertTrue(product.isPresent());
        assertEquals("Product 1", product.get().getName());
    }

    @Test
    void testFindByName() {
        Optional<Product> product = productRepository.findByName("Product 2");
        assertTrue(product.isPresent());
        assertEquals("PRODUCT_002", product.get().getProductCode());
    }

    @Test
    void testFindByCategory() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> products = productRepository.findByCategory("CATEGORY_001", pageable);
        assertEquals(1, products.getTotalElements());
        assertEquals("Product 1", products.getContent().get(0).getName());
    }

    @Test
    void testFindByBrand() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> products = productRepository.findByBrand("BRAND_002", pageable);
        assertEquals(1, products.getTotalElements());
        assertEquals("Product 2", products.getContent().get(0).getName());
    }

    @Test
    void testFindByCategoryAndBrand() {
        List<Product> products = productRepository.findByCategoryAndBrand("CATEGORY_002", "BRAND_002");
        assertEquals(1, products.size());
        assertEquals("Product 2", products.get(0).getName());
    }
}
