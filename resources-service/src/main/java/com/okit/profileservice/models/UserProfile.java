package com.okit.profileservice.models;

import com.okit.profileservice.constants.UserProfileCoreConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profile")
public class UserProfile
{
    @Id
    @Email(message = UserProfileCoreConstants.INVALID_EMAIL_MSG)
    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    @NotEmpty(message = UserProfileCoreConstants.EMPTY_FIRST_NAME_MSG)
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @NotEmpty(message = UserProfileCoreConstants.EMPTY_LAST_NAME_MSG)
    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Min(value = 1, message = UserProfileCoreConstants.INVALID_ACADEMIC_YEAR)
    @Column(name = "academic_year", nullable = false)
    private Integer academicYear;

    @Temporal(TemporalType.DATE)
    @Column(name = "dob", nullable = false, updatable = false)
    private Date dob;

    @Column(name = "icon")
    private String icon;

    @Max(value = 5, message = UserProfileCoreConstants.INVALID_RATING_MSG)
    @Column(name = "rating", nullable = false)
    private Integer rating;
}
