package com.demo.product.models.response;

import com.demo.product.entities.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String productCode;
    String name;
    String description;
    double price;
    String category;
    String brand;

    public static ProductResponse fromProduct(Product product) {
        return ProductResponse.builder()
                .productCode(product.getProductCode())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .brand(product.getBrand())
                .build();
    }

    public static List<ProductResponse> fromProductList(List<Product> products) {
        return products.stream()
                .map(ProductResponse::fromProduct)
                .toList();
    }
}