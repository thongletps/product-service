package com.demo.product.utils;


import com.demo.product.models.request.ProductRequest;

public class ProductUtils {
    private ProductUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static ProductRequest createSampleProductRequest(int index) {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductCode("PRODUCT_000" + index);
        productRequest.setCategory("CATEGORY_000");
        productRequest.setBrand("BRAND_000");
        productRequest.setProductCode("PRODUCT_000" + index);
        productRequest.setName("Product " + index);
        productRequest.setDescription("Description of Product " + index);
        productRequest.setPrice(10.00 + index); // Example price
        return productRequest;
    }
}
