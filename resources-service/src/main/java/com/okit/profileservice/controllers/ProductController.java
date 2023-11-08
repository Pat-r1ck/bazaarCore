package com.okit.profileservice.controllers;

import com.okit.profileservice.dto.DomainResponse;
import com.okit.profileservice.dto.UpdateProductRequest;
import com.okit.profileservice.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resources/product")
public class ProductController
{
    @GetMapping(value = "/query")
    public ResponseEntity<String> testProduct()
    {
        return new ResponseEntity<>("Testing", HttpStatus.OK);
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
