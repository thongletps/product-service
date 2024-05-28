package com.demo.product.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "products")
public class Product {
    @Id
    String id;

    @Indexed(unique = true)
    String productCode;

    String name;
    String description;
    double price;
    String category;
    String brand;
}