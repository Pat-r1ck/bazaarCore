package com.okit.resourceservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.okit.resourceservice.constants.ProductCoreConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Builder.Default
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private UUID uuid = UUID.randomUUID();

    @NotEmpty(message = ProductCoreConstants.EMPTY_TITLE_MSG)
    @Column(name = "title", nullable = false)
    private String title;

    @NotEmpty(message = ProductCoreConstants.EMPTY_CATEGORY_MSG)
    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "description")
    private String description;

    @Min(value = 0, message = ProductCoreConstants.INVALID_PRICE_MSG)
    @Column(name = "price", nullable = false)
    private Integer price;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image", nullable = false)
    private List<String> images = new ArrayList<>();

    @Builder.Default
    @Column(name = "available", nullable = false)
    private boolean available = true;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private UserProfile owner;
}
