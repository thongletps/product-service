package com.demo.product;

import com.demo.product.entities.Product;
import com.demo.product.services.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeDatabase(ProductService productService) {
        return (args) -> {
            String productCode = "PRODUCT_CODE";
            String categoryCode = "CATEGORY_0001";
            String brandCode = "BRAND_0001";

            // Check if product with the specified productCode already exists
            if (productService.getProductByProductCode(productCode) == null) {
                // Product does not exist, create and save a new Product
                Product product = new Product();
                product.setProductCode(productCode);
                product.setName("Sample Product");
                product.setDescription("This is a sample product.");
                product.setPrice(19.99);
                product.setCategory(categoryCode);
                product.setBrand(brandCode);
                productService.createProduct(product);
                System.out.println("Product created successfully.");
            } else {
                System.out.println("Product with productCode " + productCode + " already exists.");
            }
        };
    }

}
