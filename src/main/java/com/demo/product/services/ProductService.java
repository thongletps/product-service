package com.demo.product.services;


import com.demo.product.entities.Product;
import com.demo.product.models.request.ProductPaginationRequest;
import com.demo.product.models.response.PagingResponse;
import com.demo.product.models.response.ProductResponse;
import com.demo.product.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class ProductService {
    ProductRepository productRepository;

    PaginationService paginationService;

    public PagingResponse<ProductResponse> getAllProducts(ProductPaginationRequest paginationRequest) {
        log.info("Retrieving all products with pagination: {}", paginationRequest);
        Pageable pageable = PageRequest.of(paginationRequest.getPageIndex(), paginationRequest.getPageSize(),
                Sort.by(Sort.Direction.fromString(paginationRequest.getSortDirection()), paginationRequest.getSortBy()));
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductResponse> productResponses = ProductResponse.fromProductList(productPage.getContent());
        return PagingResponse.<ProductResponse>builder()
                .totalItems(productPage.getTotalElements())
                .items(productResponses)
                .currentPage(productPage.getNumber())
                .itemsPerPage(productPage.getSize())
                .sortBy(paginationRequest.getSortBy())
                .sortDirection(paginationRequest.getSortDirection())
                .totalPages(productPage.getTotalPages())
                .build();
    }

    public PagingResponse<ProductResponse> getAllProductsWithSearchCriteria(ProductPaginationRequest paginationRequest) {
        log.info("Retrieving all products with search criteria: {}", paginationRequest);

        Pageable pageable = PageRequest.of(paginationRequest.getPageIndex(), paginationRequest.getPageSize(),
                Sort.by(Sort.Direction.fromString(paginationRequest.getSortDirection()), paginationRequest.getSortBy()));

        if (paginationRequest.getSearchCriteria() != null && !paginationRequest.getSearchCriteria().getSearchFields().isEmpty()) {
            if (paginationRequest.getSearchCriteria().getSearchFields().containsKey("category")) {
                String category = paginationRequest.getSearchCriteria().getSearchFields().get("category").get(0);
                Page<Product> productPage = productRepository.findByCategory(category, pageable);
                return paginationService.createPagingResponse(productPage);
            } else if (paginationRequest.getSearchCriteria().getSearchFields().containsKey("brand")) {
                String brand = paginationRequest.getSearchCriteria().getSearchFields().get("brand").get(0);
                Page<Product> productPage = productRepository.findByBrand(brand, pageable);
                return paginationService.createPagingResponse(productPage);
            }
        }

        // If no specific search criteria is provided, return all products
        Page<Product> productPage = productRepository.findAll(pageable);
        return paginationService.createPagingResponse(productPage);
    }

    private PagingResponse<ProductResponse> createPagingResponse(Page<Product> productPage) {
        List<ProductResponse> productResponses = ProductResponse.fromProductList(productPage.getContent());
        return PagingResponse.<ProductResponse>builder()
                .totalItems(productPage.getTotalElements())
                .items(productResponses)
                .currentPage(productPage.getNumber())
                .itemsPerPage(productPage.getSize())
                .totalPages(productPage.getTotalPages())
                .build();
    }

    public Product getProductById(String id) {
        log.info("Retrieving product by id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product getProductByProductCode(String productCode) {
        log.info("Retrieving product by productCode: {}", productCode);
        Optional<Product> optionalProduct = productRepository.findByProductCode(productCode);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            log.error("Product not found with productCode: {}", productCode);
            return null; // Return null if product with productCode is not found
        }
    }


    public Product getProductByName(String name) {
        log.info("Retrieving product by name: {}", name);
        return productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found with name: " + name));
    }

    public Product createProduct(Product product) {
        log.info("Creating product: {}", product);
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product productDetails) {
        log.info("Updating product with id: {}", id);
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        return productRepository.save(product);
    }

    public ResponseEntity<?> deleteProduct(String id) {
        log.info("Deleting product with id: {}", id);
        Product product = getProductById(id);
        productRepository.delete(product);
        return ResponseEntity.ok().build();
    }

    public void deleteAllProducts() {
        log.info("Deleting all products");
        productRepository.deleteAll();
    }
}
