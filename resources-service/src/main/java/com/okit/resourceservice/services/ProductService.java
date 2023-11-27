package com.okit.resourceservice.services;

import com.okit.resourceservice.dto.CreateProductRequest;
import com.okit.resourceservice.dto.GenericResponse;
import com.okit.resourceservice.dto.ProductDetailRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Service
public interface ProductService
{
    GenericResponse createProduct(CreateProductRequest request, String email) throws IOException;
    // GenericResponse updateProduct(ProductDetailRequest request, String id, String email) throws IOException;
    // GenericResponse deleteProduct(String id, String email) throws IOException;

    GenericResponse getAllProduct();
    GenericResponse getProduct(UUID uuid);
    GenericResponse getUserProductsListing(String email);

    // Product getProduct(String id);
    // Set<Product> getProductByEmail(String email);
    // GenericResponse updateProduct(ProductDetailRequest request, String id, String email) throws IOException;
}
