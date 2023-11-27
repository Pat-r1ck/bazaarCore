package com.okit.resourceservice.controllers;

import com.okit.resourceservice.dto.CreateProductRequest;
import com.okit.resourceservice.dto.DomainResponse;
import com.okit.resourceservice.dto.ProductDetailRequest;
import com.okit.resourceservice.models.Product;
import com.okit.resourceservice.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resources/products")
public class ProductController
{
    @PostMapping(value = "/",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> registerProduct(
        @ModelAttribute @Valid CreateProductRequest productRequest,
        HttpServletRequest request
    ) throws IOException
    {
        final String email = request.getHeader("email");

        return new ResponseEntity<>(
                productService.createProduct(productRequest, email).getResponse(),
                HttpStatus.OK);
    }
    
    @GetMapping(value = "/")
    public ResponseEntity<Object> getAllProduct()
    {
        return new ResponseEntity<>(
                productService.getAllProduct(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<Object> getProduct(@PathVariable String uuid)
    {
        UUID uuidObj = UUID.fromString(uuid);
        return new ResponseEntity<>(
            productService.getProduct(uuidObj).getResponse(),
            HttpStatus.OK
        );
    }
    
    @GetMapping(value = "/user")
    public ResponseEntity<Object> getUserProductsListing(HttpServletRequest request)
    {
        final String email = request.getHeader("email");
        return new ResponseEntity<>(
            productService.getUserProductsListing(email).getResponse(),
            HttpStatus.OK
        );
    }

    // @GetMapping(value = "/query/{id}")
    // public ResponseEntity<Product> getProduct(@PathVariable String id)
    // {
    //     return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    // }

    // @GetMapping(value = "/query")
    // public ResponseEntity<Set<Product>> getProductByEmail(@RequestParam(name = "email") String email)
    // {
    //     return new ResponseEntity<>(productService.getProductByEmail(email), HttpStatus.OK);
    // }



    // @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // public ResponseEntity<DomainResponse> updateProduct(
    //         @ModelAttribute ProductDetailRequest productRequest,
    //         @PathVariable(value = "id") String id,
    //         HttpServletRequest request
    // ) throws IOException
    // {
    //     final String email = request.getHeader("email");

    //     return new ResponseEntity<>(
    //             productService.updateProduct(productRequest, id, email),
    //             HttpStatus.CREATED
    //     );
    // }

    @Autowired
    private final ProductService productService;
}
