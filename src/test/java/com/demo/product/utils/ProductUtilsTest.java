package com.demo.product.utils;

import com.demo.product.models.request.ProductRequest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class ProductUtilsTest {

    @Test
    void testCreateSampleProductRequest() {
        int index = 1;
        ProductRequest productRequest = ProductUtils.createSampleProductRequest(index);

        assertNotNull(productRequest);
        assertEquals("PRODUCT_0001", productRequest.getProductCode());
        assertEquals("CATEGORY_000", productRequest.getCategory());
        assertEquals("BRAND_000", productRequest.getBrand());
        assertEquals("Product 1", productRequest.getName());
        assertEquals("Description of Product 1", productRequest.getDescription());
        assertEquals(11.00, productRequest.getPrice());
    }

    @Test
    void testPrivateConstructor() {
        try {
            Constructor<ProductUtils> constructor = ProductUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
            fail("Expected UnsupportedOperationException to be thrown");
        } catch (InvocationTargetException e) {
            // InvocationTargetException wraps the exception thrown by the constructor
            Throwable targetException = e.getTargetException();
            assertInstanceOf(UnsupportedOperationException.class, targetException);
            assertEquals("This is a utility class and cannot be instantiated", targetException.getMessage());
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            fail("Unexpected exception: " + e);
        }
    }
}
