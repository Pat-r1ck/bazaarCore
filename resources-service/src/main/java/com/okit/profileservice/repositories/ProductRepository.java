package com.okit.profileservice.repositories;

import com.okit.profileservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>
{
}
