package com.okit.resourceservice.services;

import com.okit.resourceservice.dto.DomainResponse;
import com.okit.resourceservice.dto.ProductDetailRequest;
import com.okit.resourceservice.models.Product;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
public interface ProductService
{
    Product getProduct(String id);
    Set<Product> getProductByEmail(String email);
    DomainResponse registerProduct(ProductDetailRequest request, String email) throws IOException;
    DomainResponse updateProduct(ProductDetailRequest request, String id, String email) throws IOException;
}
