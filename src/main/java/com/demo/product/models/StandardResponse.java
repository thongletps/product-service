package com.demo.product.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StandardResponse<T> {
    @Builder.Default
    private int code = 1000;
    private T data;
    private String message;

    // Static methods for creating response instances

    public static <T> StandardResponse<T> success(T data, String message) {
        return StandardResponse.<T>builder()
                .data(data)
                .message(message)
                .build();
    }

    public static <T> StandardResponse<T> error(String message) {
        return StandardResponse.<T>builder()
                .data(null)
                .message(message)
                .build();
    }

    // Method to check if the response represents a successful operation
    public boolean isSuccess() {
        return code == 1000; // Assuming 1000 represents success
    }
}
