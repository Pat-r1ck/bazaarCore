package com.okit.resourceservice.services;

import com.okit.resourceservice.dto.CreateUserProfileRequest;
import com.okit.resourceservice.dto.CreateUserProfileResponse;
import com.okit.resourceservice.dto.GenericResponse;
import com.okit.resourceservice.dto.UpdateUserProfileRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
public interface UserProfileService
{
    CreateUserProfileResponse createUserProfile(CreateUserProfileRequest request, String email) throws ParseException, IOException;
    GenericResponse getUserProfile(String email);
    GenericResponse updateUserProfile(UpdateUserProfileRequest request, String email) throws ParseException, IOException;
}
