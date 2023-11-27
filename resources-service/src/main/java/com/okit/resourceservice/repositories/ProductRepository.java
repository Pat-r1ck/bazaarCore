package com.okit.resourceservice.repositories;

import com.okit.resourceservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>
{
    @Query(value = "select * from product where id = ?1 limit 1",nativeQuery = true)
    Optional<Product> findByUUID(UUID uuid);

    // use this to get all products of a user by using many to one relationship
    @Query(value = "select * from product where owner = ?1", nativeQuery = true)
    Set<Product> getAllOwnerProductsById(Long ownerId);

    @Query(value = "select * from product", nativeQuery = true)
    Set<Product> getAllProducts();
}
