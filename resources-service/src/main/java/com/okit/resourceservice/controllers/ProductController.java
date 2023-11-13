package com.okit.resourceservice.controllers;

import com.okit.resourceservice.dto.DomainResponse;
import com.okit.resourceservice.dto.UpdateProductRequest;
import com.okit.resourceservice.models.Product;
import com.okit.resourceservice.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resources/product")
public class ProductController
{
    @GetMapping(value = "/query/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id)
    {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @GetMapping(value = "/query")
    public ResponseEntity<Set<Product>> getProductByEmail(@RequestParam(name = "email") String email)
    {
        return new ResponseEntity<>(productService.getProductByEmail(email), HttpStatus.OK);
    }

    @PostMapping(value = "/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DomainResponse> updateProduct(
            @ModelAttribute UpdateProductRequest productRequest,
            HttpServletRequest request
    ) throws IOException
    {
        final String email = request.getHeader("email");

        return new ResponseEntity<>(productService.updateProduct(productRequest, email), HttpStatus.OK);
    }

    @Autowired
    private final ProductService productService;
}
