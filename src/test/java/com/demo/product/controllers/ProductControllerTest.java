package com.demo.product.controllers;

import com.demo.product.facade.ProductFacade;
import com.demo.product.models.StandardResponse;
import com.demo.product.models.request.ProductPaginationRequest;
import com.demo.product.models.response.PagingResponse;
import com.demo.product.models.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Mock
    private ProductFacade productFacade;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        ProductPaginationRequest paginationRequest = new ProductPaginationRequest();
        PagingResponse<ProductResponse> dummyResponse = new PagingResponse<>();
        when(productFacade.getAllProducts(any())).thenReturn(dummyResponse);

        // Act
        ResponseEntity<?> responseEntity = productController.getAllProducts(paginationRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dummyResponse, ((StandardResponse<?>) Objects.requireNonNull(responseEntity.getBody())).getData());
        verify(productFacade).getAllProducts(any());
    }

    // Similar tests for other endpoints...

    @Test
    void testInitializeDatabase() {
        // Act
        ResponseEntity<?> responseEntity = productController.initializeDatabase();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Database initialized successfully", ((StandardResponse<?>) Objects.requireNonNull(responseEntity.getBody())).getMessage());
    }

}
