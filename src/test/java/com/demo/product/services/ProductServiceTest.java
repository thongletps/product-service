package com.demo.product.services;

import com.demo.product.entities.Product;
import com.demo.product.models.request.ProductPaginationRequest;
import com.demo.product.models.response.PagingResponse;
import com.demo.product.models.response.ProductResponse;
import com.demo.product.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PaginationService paginationService;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId("1");
        product.setProductCode("PRODUCT_001");
        product.setName("Product 1");
        product.setCategory("CATEGORY_001");
        product.setBrand("BRAND_001");
        product.setPrice(10.00);
    }

    @Test
    void testGetAllProducts() {
        ProductPaginationRequest paginationRequest = new ProductPaginationRequest();
        paginationRequest.setPageIndex(0);
        paginationRequest.setPageSize(10);
        paginationRequest.setSortBy("name");
        paginationRequest.setSortDirection("asc");

        List<Product> productList = Collections.singletonList(product);
        Page<Product> productPage = new PageImpl<>(productList);

        when(productRepository.findAll(Mockito.any(Pageable.class))).thenReturn(productPage);

        // You need to manually create a list containing a ProductResponse since it's not a mock object
        List<ProductResponse> productResponseList = Collections.singletonList(ProductResponse.fromProduct(product));

        PagingResponse<ProductResponse> expectedResponse = PagingResponse.<ProductResponse>builder()
                .totalItems(productPage.getTotalElements())
                .items(productResponseList)
                .currentPage(productPage.getNumber())
                .itemsPerPage(productPage.getSize())
                .sortBy(paginationRequest.getSortBy())
                .sortDirection(paginationRequest.getSortDirection())
                .totalPages(productPage.getTotalPages())
                .build();

        // Invoke the method under test
        PagingResponse<ProductResponse> response = productService.getAllProducts(paginationRequest);

        assertNotNull(response);
        assertEquals(expectedResponse.getTotalItems(), response.getTotalItems());
        // Add more assertions based on your requirements

        verify(productRepository, times(1)).findAll(Mockito.any(Pageable.class));
    }


    @Test
    void testGetProductById() {
        when(productRepository.findById("1")).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById("1");

        assertNotNull(foundProduct);
        assertEquals("PRODUCT_001", foundProduct.getProductCode());
        verify(productRepository, times(1)).findById("1");
    }

    @Test
    void testGetProductByProductCode() {
        when(productRepository.findByProductCode("PRODUCT_001")).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductByProductCode("PRODUCT_001");

        assertNotNull(foundProduct);
        assertEquals("Product 1", foundProduct.getName());
        verify(productRepository, times(1)).findByProductCode("PRODUCT_001");
    }

    @Test
    void testGetProductByName() {
        when(productRepository.findByName("Product 1")).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductByName("Product 1");

        assertNotNull(foundProduct);
        assertEquals("PRODUCT_001", foundProduct.getProductCode());
        verify(productRepository, times(1)).findByName("Product 1");
    }

    @Test
    void testCreateProduct() {
        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct);
        assertEquals("PRODUCT_001", createdProduct.getProductCode());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProduct() {
        Product updatedProductDetails = new Product();
        updatedProductDetails.setName("Updated Product");
        updatedProductDetails.setDescription("Updated Description");
        updatedProductDetails.setPrice(20.00);

        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.updateProduct("1", updatedProductDetails);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        verify(productRepository, times(1)).findById("1");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.findById("1")).thenReturn(Optional.of(product));

        productService.deleteProduct("1");

        verify(productRepository, times(1)).findById("1");
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteAllProducts() {
        productService.deleteAllProducts();

        verify(productRepository, times(1)).deleteAll();
    }
}
