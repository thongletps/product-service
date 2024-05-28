package com.demo.product.models.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductPaginationRequest {
    int pageIndex;
    int pageSize;
    String sortBy;
    String sortDirection;
    String keyword;
    SearchCriteria searchCriteria;
}
