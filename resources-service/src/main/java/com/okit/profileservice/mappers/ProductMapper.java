package com.okit.profileservice.mappers;

import com.okit.profileservice.dto.UpdateProductRequest;
import com.okit.profileservice.models.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class ProductMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateProduct(UpdateProductRequest request, @MappingTarget Product product);
}
