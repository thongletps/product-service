package com.demo.product.facade.impl;

import com.demo.product.entities.Product;
import com.demo.product.facade.ProductFacade;
import com.demo.product.models.request.ProductPaginationRequest;
import com.demo.product.models.request.ProductRequest;
import com.demo.product.models.response.PagingResponse;
import com.demo.product.models.response.ProductResponse;
import com.demo.product.services.ProductService;
import org.springframework.stereotype.Component;

@Component
public class ProductFacadeImpl implements ProductFacade {

    private final ProductService productService;

    public ProductFacadeImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public PagingResponse<ProductResponse> getAllProducts(ProductPaginationRequest paginationRequest) {
        // Check if searchCriteria is null or empty
        if (paginationRequest.getSearchCriteria() == null || paginationRequest.getSearchCriteria().getSearchFields().isEmpty()) {
            // Handle case where no search criteria is specified
            return productService.getAllProducts(paginationRequest);
        } else {
            // Handle case where search criteria is specified
            return productService.getAllProductsWithSearchCriteria(paginationRequest);
        }
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = productService.getProductById(id);
        return ProductResponse.fromProduct(product);
    }

    @Override
    public ProductResponse getProductByProductCode(String productCode) {
        Product product = productService.getProductByProductCode(productCode);
        return ProductResponse.fromProduct(product);
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest.toProduct());
        return ProductResponse.fromProduct(product);
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        Product product = productService.updateProduct(id, productRequest.toProduct());
        return ProductResponse.fromProduct(product);
    }

    @Override
    public void deleteProduct(String id) {
        productService.deleteProduct(id);
    }

    @Override
    public void deleteAllProducts() {
        productService.deleteAllProducts();
    }
}
