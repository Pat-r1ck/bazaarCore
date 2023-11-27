package com.okit.resourceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileRequest
{
    private String firstName;
    private String lastName;
    private Integer academicYear;
    private String dob;

    @Builder.Default
    private MultipartFile icon = null;
}
