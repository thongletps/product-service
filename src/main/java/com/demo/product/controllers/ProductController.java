package com.demo.product.controllers;

import com.demo.product.facade.ProductFacade;
import com.demo.product.models.StandardResponse;
import com.demo.product.models.request.ProductPaginationRequest;
import com.demo.product.models.request.ProductRequest;
import com.demo.product.models.response.PagingResponse;
import com.demo.product.models.response.ProductResponse;
import com.demo.product.utils.ProductUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductFacade productFacade;

    @GetMapping
    public ResponseEntity<StandardResponse<PagingResponse<ProductResponse>>> getAllProducts(@RequestBody ProductPaginationRequest paginationRequest) {
        PagingResponse<ProductResponse> products = productFacade.getAllProducts(paginationRequest);
        return ResponseEntity.ok(StandardResponse.<PagingResponse<ProductResponse>>builder()
                .data(products)
                .message("Successfully retrieved products")
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse<ProductResponse>> getProductById(@PathVariable String id) {
        ProductResponse product = productFacade.getProductById(id);
        return ResponseEntity.ok(StandardResponse.<ProductResponse>builder()
                .data(product)
                .message("Successfully retrieved product with id: " + id)
                .build());
    }

    @GetMapping("/code/{productCode}")
    public ResponseEntity<StandardResponse<ProductResponse>> getProductByProductCode(@PathVariable String productCode) {
        ProductResponse product = productFacade.getProductByProductCode(productCode);
        return ResponseEntity.ok(StandardResponse.<ProductResponse>builder()
                .data(product)
                .message("Successfully retrieved product with code: " + productCode)
                .build());
    }

    @PostMapping
    public ResponseEntity<StandardResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse createdProduct = productFacade.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(StandardResponse.<ProductResponse>builder()
                .data(createdProduct)
                .message("Product created successfully")
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponse<ProductResponse>> updateProduct(@PathVariable String id, @Valid @RequestBody ProductRequest productRequest) {
        ProductResponse updatedProduct = productFacade.updateProduct(id, productRequest);
        return ResponseEntity.ok(StandardResponse.<ProductResponse>builder()
                .data(updatedProduct)
                .message("Product updated successfully")
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponse<Void>> deleteProduct(@PathVariable String id) {
        productFacade.deleteProduct(id);
        return ResponseEntity.ok(StandardResponse.<Void>builder()
                .message("Product deleted successfully")
                .build());
    }

    @DeleteMapping
    public ResponseEntity<StandardResponse<Void>> deleteAllProducts() {
        productFacade.deleteAllProducts();
        return ResponseEntity.ok(StandardResponse.<Void>builder()
                .message("All products deleted successfully")
                .build());
    }

    @PostMapping("/initialize")
    public ResponseEntity<StandardResponse<String>> initializeDatabase() {
        for (int i = 0; i < 100; i++) {
            ProductRequest productRequest = ProductUtils.createSampleProductRequest(i);
            productFacade.createProduct(productRequest);
        }
        return ResponseEntity.ok(StandardResponse.<String>builder()
                .data("Successfully added 100 products")
                .message("Database initialized successfully")
                .build());
    }
}
