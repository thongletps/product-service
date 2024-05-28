package com.demo.product.facade;

import com.demo.product.models.request.ProductPaginationRequest;
import com.demo.product.models.request.ProductRequest;
import com.demo.product.models.response.PagingResponse;
import com.demo.product.models.response.ProductResponse;

public interface ProductFacade {
    PagingResponse<ProductResponse> getAllProducts(ProductPaginationRequest paginationRequest);

    ProductResponse getProductById(String id);

    ProductResponse getProductByProductCode(String productCode);

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(String id, ProductRequest productRequest);

    void deleteProduct(String id);

    void deleteAllProducts();
}
