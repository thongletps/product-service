package com.demo.product.models.request;

import com.demo.product.entities.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotBlank
    @Size(min = 2, max = 20)
    String productCode;

    @NotBlank
    @Size(min = 2, max = 100)
    String name;

    @NotBlank
    @Size(max = 255)
    String description;

    @NotNull
    @Min(0)
    double price;

    @NotBlank
    String category;

    @NotBlank
    String brand;

    public Product toProduct() {
        return Product.builder()
                .productCode(this.productCode)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .category(this.category)
                .brand(this.brand)
                .build();
    }
}