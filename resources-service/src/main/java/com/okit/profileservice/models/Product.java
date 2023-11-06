package com.okit.profileservice.models;

import com.okit.profileservice.constants.ProductCoreConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @NotEmpty(message = ProductCoreConstants.EMPTY_TITLE_MSG)
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Min(value = 0, message = ProductCoreConstants.INVALID_PRICE_MSG)
    @Column(name = "price", nullable = false)
    private Integer price;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image", nullable = false)
    private Set<String> images = new HashSet<>();

    @Builder.Default
    @Column(name = "available", nullable = false)
    private boolean available = false;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private UserProfile owner;
}
