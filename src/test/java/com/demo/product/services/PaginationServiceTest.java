package com.demo.product.services;

import com.demo.product.entities.Product;
import com.demo.product.models.response.PagingResponse;
import com.demo.product.models.response.ProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaginationServiceTest {

    @Mock
    private Page<Product> productPage;

    @Test
    void testCreatePagingResponse() {
        // Mocking the necessary data
        List<Product> productList = Collections.singletonList(new Product());
        when(productPage.getContent()).thenReturn(productList);
        when(productPage.getTotalElements()).thenReturn(1L);
        when(productPage.getNumber()).thenReturn(0);
        when(productPage.getSize()).thenReturn(10);
        when(productPage.getTotalPages()).thenReturn(1);

        // Creating the instance of PaginationService
        PaginationService paginationService = new PaginationService();

        // Calling the method under test
        PagingResponse<ProductResponse> pagingResponse = paginationService.createPagingResponse(productPage);

        // Asserting the result
        assertEquals(1L, pagingResponse.getTotalItems());
        assertEquals(1, pagingResponse.getItems().size());
        assertEquals(0, pagingResponse.getCurrentPage());
        assertEquals(10, pagingResponse.getItemsPerPage());
        assertEquals(1, pagingResponse.getTotalPages());
    }
}
