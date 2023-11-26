package com.okit.resourceservice.dto;

import org.springframework.context.annotation.Description;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserProfileRequest {

    @NotBlank(message = "first name cannot be blank")
    private String firstName;

    @NotBlank(message = "last name cannot be blank")
    private String lastName;

    @NotNull(message = "academic year cannot be blank")
    private Integer academicYear;

    @NotBlank(message = "date of birth cannot be blank")
    private String dob;
    
    private MultipartFile icon;
}
