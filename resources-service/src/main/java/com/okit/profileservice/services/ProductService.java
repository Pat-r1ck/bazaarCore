package com.okit.profileservice.services;

import com.okit.profileservice.dto.DomainResponse;
import com.okit.profileservice.dto.UpdateProductRequest;
import com.okit.profileservice.models.Product;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Service
public interface ProductService
{
    Product getProduct(String id);
    Set<Product> getProductByEmail(String email);
    DomainResponse updateProduct(UpdateProductRequest request, String email) throws IOException;
}
