package com.okit.profileservice.services;

import com.okit.profileservice.dto.DomainResponse;
import com.okit.profileservice.dto.UpdateProductRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ProductService
{
    DomainResponse updateProduct(UpdateProductRequest request, String email) throws IOException;
}
