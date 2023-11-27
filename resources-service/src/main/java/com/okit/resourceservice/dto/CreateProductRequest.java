package com.okit.resourceservice.dto;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "Product title cannot be blank")
    private String title;

    @NotBlank(message = "Product category cannot be blank")
    private String category;

    private String description;

    @NotNull(message = "Product price cannot be blank")
    private Integer price;

    @Builder.Default
    private List<MultipartFile> images = null;
}
