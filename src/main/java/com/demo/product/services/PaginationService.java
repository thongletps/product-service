package com.demo.product.services;

import com.demo.product.entities.Product;
import com.demo.product.models.response.PagingResponse;
import com.demo.product.models.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginationService {

    public PagingResponse<ProductResponse> createPagingResponse(Page<Product> productPage) {
        List<ProductResponse> productResponses = ProductResponse.fromProductList(productPage.getContent());
        return PagingResponse.<ProductResponse>builder()
                .totalItems(productPage.getTotalElements())
                .items(productResponses)
                .currentPage(productPage.getNumber())
                .itemsPerPage(productPage.getSize())
                .totalPages(productPage.getTotalPages())
                .build();
    }
}

