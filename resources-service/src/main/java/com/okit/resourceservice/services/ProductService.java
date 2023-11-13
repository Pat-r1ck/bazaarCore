package com.okit.resourceservice.services;

import com.okit.resourceservice.dto.DomainResponse;
import com.okit.resourceservice.dto.UpdateProductRequest;
import com.okit.resourceservice.models.Product;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
public interface ProductService
{
    Product getProduct(String id);
    Set<Product> getProductByEmail(String email);
    DomainResponse updateProduct(UpdateProductRequest request, String email) throws IOException;
}
