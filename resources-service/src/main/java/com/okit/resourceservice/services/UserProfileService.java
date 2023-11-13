package com.okit.resourceservice.services;

import com.okit.resourceservice.dto.UpdateUserProfileRequest;
import com.okit.resourceservice.dto.UpdateUserProfileResponse;
import com.okit.resourceservice.models.UserProfile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
public interface UserProfileService
{
    UserProfile getUserProfile(String email);
    UpdateUserProfileResponse updateUserProfile(UpdateUserProfileRequest request, String email) throws ParseException, IOException;
    UpdateUserProfileResponse deleteUserIcon(String email);
}
