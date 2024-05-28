package com.demo.product.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagingResponse<T> {
    long totalItems;
    List<T> items;
    int currentPage;
    int itemsPerPage;
    String sortBy;
    String sortDirection;
    int totalPages;
}